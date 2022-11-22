
package com.example.beaware;

	import android.graphics.*;
	import android.os.Bundle;
	import android.util.SparseArray;
	import android.widget.Toast;
	import androidx.appcompat.app.AlertDialog;
	import androidx.appcompat.app.AppCompatActivity;
    import com.example.beaware.Models.Box;
    import com.google.android.gms.vision.Frame;
	import com.google.android.gms.vision.face.Face;
	import com.google.android.gms.vision.face.FaceDetector;
	import com.otaliastudios.cameraview.CameraView;
	import org.tensorflow.lite.DataType;
	import org.tensorflow.lite.Interpreter;
	import org.tensorflow.lite.support.common.FileUtil;
	import org.tensorflow.lite.support.common.ops.NormalizeOp;
	import org.tensorflow.lite.support.image.ImageProcessor;
	import org.tensorflow.lite.support.image.TensorImage;
	import org.tensorflow.lite.support.image.ops.ResizeOp;
	import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
	import org.tensorflow.lite.support.label.TensorLabel;
	import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
	import java.io.ByteArrayOutputStream;
	import java.io.IOException;
	import java.nio.MappedByteBuffer;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;



	import exportkit.xd.R;

    public class scan_face_activity extends AppCompatActivity {

    	CameraView cameraView;
    	OverlayView overlayView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_face);
        overlayView = findViewById(R.id.overlayView);
		cameraView = findViewById(R.id.cameraView);
		cameraView.setLifecycleOwner(this);
		FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(true)
				.build();
		if (!faceDetector.isOperational()) {
			new AlertDialog.Builder(this)
					.setMessage("Could not set up the face detector!")
					.show();
		}
		cameraView.addFrameProcessor(frame -> {

			Matrix matrix =new Matrix();
			matrix.setRotate((float) frame.getRotationToUser());

			if (frame.getDataClass() == byte[].class){
				ByteArrayOutputStream out =new ByteArrayOutputStream();
				YuvImage yuvImage = new YuvImage(
						frame.getData(),
						ImageFormat.NV21,
						frame.getSize().getWidth(),
						frame.getSize().getHeight(),
						null
				);
				yuvImage.compressToJpeg(
						new Rect(0, 0, frame.getSize().getWidth(), frame.getSize().getHeight()), 100, out
				);
				byte[] imageBytes = out.toByteArray();
				Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

				bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				bitmap = Bitmap.createScaledBitmap(bitmap, overlayView.getWidth(), overlayView.getHeight(), true);

				try {
					overlayView.setBoundingBox(processBitmap(bitmap, faceDetector));

				} catch (IOException e) {
					e.printStackTrace();
				}
				overlayView.invalidate();
			} else {
				Toast.makeText(this, "Camera Data not Supported", Toast.LENGTH_LONG).show();
			}
		});



	}

		private ArrayList<Box> processBitmap(Bitmap bitmap , FaceDetector faceDetector) throws IOException {
			ArrayList<Box> boundingBoxList = new ArrayList();

			// Detect the faces
			Frame frame = new Frame.Builder().setBitmap(bitmap).build();
			SparseArray<Face> faces = faceDetector.detect(frame);

			// Mark out the identified face
			for (int i=0;i < faces.size(); i++) {
				Face thisFace = faces.valueAt(i);
				float left = thisFace.getPosition().x;
				float top = thisFace.getPosition().y;
				float right = left + thisFace.getWidth();
				float bottom = top + thisFace.getHeight();
				float thisRight,thisBottom;
				if ((int)right > (int)bitmap.getWidth()) {
					thisRight = bitmap.getWidth() - (int)left;
				} else {
					thisRight =thisFace.getWidth();
				}
				if ((int)bottom > bitmap.getHeight()) {
				thisBottom =bitmap.getHeight() - (int)top;
				} else {
				thisBottom =thisFace.getHeight();
				}
				Bitmap bitmapCropped = Bitmap.createBitmap(bitmap, (int)left, (int)top,(int)thisRight,(int)thisBottom);


				Map<String,Float> label = predict(bitmapCropped);
				String predictionn = "";
				float with = 0, without= 0;
				if (label.get("WithMask") != null)
				{
					with = label.get("WithMask");
				}

				if (label.get("WithoutMask") != null)
				{
					 without =label.get("WithoutMask");
				}


				if (with > without){
					predictionn = "With Mask : " + String.format("%.1f", with*100) + "%";

				} else {
					predictionn = "Without Mask : " + String.format("%.1f", without*100) + "%";
				}

				boundingBoxList.add(new Box(new RectF(left,top,right,bottom), predictionn, with>without));
			}
			return boundingBoxList;
		}

		private Map<String,Float> predict(Bitmap input) throws IOException {
			// load model
			MappedByteBuffer modelFile = FileUtil.loadMappedFile(getApplicationContext(), "model.tflite");
			Interpreter model = new Interpreter(modelFile, new Interpreter.Options());
			List<String> labels = FileUtil.loadLabels(this, "labels.txt");

			// data type
			 DataType imageDataType = model.getInputTensor(0).dataType();
			int[] inputShape = model.getInputTensor(0).shape();

			DataType outputDataType = model.getOutputTensor(0).dataType();
			int[] outputShape = model.getOutputTensor(0).shape();

			TensorImage inputImageBuffer = new TensorImage(imageDataType);
			TensorBuffer outputBuffer = TensorBuffer.createFixedSize(outputShape, outputDataType);

			// preprocess
			int cropSize = Math.min(input.getWidth(), input.getHeight());
			ImageProcessor imageProcessor = new ImageProcessor.Builder()
					.add(new ResizeWithCropOrPadOp(cropSize,cropSize))
					.add(new ResizeOp(inputShape[1], inputShape[2], ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
					.add(new NormalizeOp(127.5f, 127.5f))
					.build();

			// load image
			inputImageBuffer.load(input);
			inputImageBuffer = imageProcessor.process(inputImageBuffer);

			// run model
			model.run(inputImageBuffer.getBuffer(), outputBuffer.getBuffer().rewind());

			// get output
			TensorLabel labelOutput = new TensorLabel(labels, outputBuffer);

			Map<String,Float> mlabel = labelOutput.getMapWithFloatValue();
			return mlabel;
		}
}
	
	