/**
 * 
 */
package adm.Activities;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import adm.Repository.GlobalParameters;
import adm.Submit.SimpleResponse;
import adm.Submit.Submit;
import adm.Twitter.TwitterJ;
import adm.User.User;
import adm.User.UserLoginResponse;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author AndroIT
 * 
 */
public class TakePhoto extends Activity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int SELECT_PHOTO = 200;

	private int idPlace;
	private String placeName;
	private String fileRoute;
	private Uri fileUri;
	public static final String TAKE_PHOTO_PREFS = "TakePhotoPrefs";
	public static final int PREFS_MODE = Activity.MODE_PRIVATE;

	@Override
	protected void onPause() {
		super.onPause();
		savePreferences();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final User user = new User(GlobalParameters.BASE_URL_JSON, this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.takephoto);

		SharedPreferences myPrefs = getSharedPreferences(TAKE_PHOTO_PREFS,
				PREFS_MODE);
		fileRoute = myPrefs.getString("fileRoute", "");

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			idPlace = extras.getInt("idPlace");
			placeName = extras.getString("placeName");
		} else {
			this.finish();
		}

		Log.d("TakePhoto", "Entro en onCreate");
		Button btnTake = (Button) findViewById(R.id.btnTakePhoto);
		btnTake.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
	
					File newFile = getOutputMediaFile();
					fileRoute = newFile.getAbsolutePath().toString();
					setFileUri(getOutputMediaFileUri(newFile));
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	
					Log.d("TakePhoto", "Envio intent a la camara");
					startActivityForResult(intent,
							CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				}catch(Exception e){
					Toast t  = Toast.makeText(getApplicationContext(), getResources().getString(R.string.photo_error), Toast.LENGTH_SHORT);
					t.show();
				}
			}

		});

		Button btnUpload = (Button) findViewById(R.id.btnUploadPhoto);
		btnUpload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (user.hasAuthKey()) {
					EditText title = (EditText) findViewById(R.id.etPhotoTitle);
					if (title.getText() == null
							|| title.getText().toString().length() < 1) {
						Toast.makeText(
								getApplicationContext(),
								"Debes escribir un texto como titulo de la imagen",
								Toast.LENGTH_SHORT).show();
					} else if (fileRoute != "") {
						if ((user.getDate() == null)
								|| (user.isAuthExpired(new Date()))) {

							UserLoginResponse ulr = user.login(
									user.getUserName(), user.getPassword(),
									GlobalParameters.API_KEY);
							if ((ulr != null) && (ulr.getStatus() == 0)) {
								user.setCredentials(user.getUserName(),
										ulr.getResult(), user.getPassword());
							}
						}

						AsyncTaskSubmitPicture task = new AsyncTaskSubmitPicture(
								TakePhoto.this, user);
						task.execute(idPlace);
					} else {
						Toast.makeText(getApplicationContext(),
								"No has hecho/seleccionado imagen a subir!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					startActivity(new Intent(TakePhoto.this,
							UserLoginActivity.class));
				}
			}

		});

		TextView actionBar = (TextView) findViewById(R.id.actionBarTakePhoto);
		actionBar.setText(R.string.newPhoto);

		EditText et = (EditText) findViewById(R.id.etPhotoTitle);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInputFromInputMethod(et.getWindowToken(), 0);

		Button btnGallery = (Button) findViewById(R.id.btnPickPhoto);
		btnGallery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
			}

		});

		showImage();
	}

	private void setFileUri(Uri fileUri) {
		this.fileUri = fileUri;
	}

	public Uri getFileUri() {
		return this.fileUri;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			// PARA RECIBIR RESULTADO DE HACER UNA FOTO
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				if (data != null) {
					Toast.makeText(this, "Image saved to:\n" + data.getData(),
							Toast.LENGTH_LONG).show();
				} else {
					savePreferences();
					showImage();
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				fileRoute = "";
				savePreferences();
				showImage();
			} else {
				// Image capture failed, advise user
				fileRoute = "";
				savePreferences();
				showImage();
			}
		} else if (requestCode == SELECT_PHOTO) {
			// PARA RECIBIR EL RESULTADO DE SELECCIONAR IMAGEN DE LA GALERÍA
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				if (data != null) {
					Uri imageURI = data.getData();
					String route = getRealPathFromURI(imageURI);
					Log.d("photoFromGallery", "Fichero de la galeria en:"
							+ route);

					fileRoute = route;
					savePreferences();
					showImage();
				} else {
					showImage();
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				fileRoute = "";
				savePreferences();
				showImage();
			} else {
				// Image capture failed, advise user
				fileRoute = "";
				savePreferences();
				showImage();
			}
		}
	}

	/** Create a file Uri for saving an image or video */
	public static Uri getOutputMediaFileUri() {
		return Uri.fromFile(getOutputMediaFile());
	}

	public static Uri getOutputMediaFileUri(File f) {
		return Uri.fromFile(f);
	}

	public static File getOutputMediaFile() {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"BonVoyage");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("BonVoyage", "Error al crear directorio en la SD");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_BV_" + timeStamp + ".jpg");
		return mediaFile;
	}

	protected void savePreferences() {
		SharedPreferences myPrefs = getSharedPreferences(TAKE_PHOTO_PREFS,
				PREFS_MODE);
		Editor editor = myPrefs.edit();

		editor.putString("fileRoute", fileRoute);
		editor.commit();
	}

	protected void showImage() {
		ImageView iv = (ImageView) findViewById(R.id.ivImageToSend);
		Button btnSend = (Button) findViewById(R.id.btnUploadPhoto);
		TextView tvPhoto = (TextView) findViewById(R.id.tvPhotoPreview);
		if (fileRoute != "") {
			btnSend.setVisibility(Button.VISIBLE);
			tvPhoto.setVisibility(TextView.VISIBLE);
			iv.setVisibility(ImageView.VISIBLE);
			iv.setImageBitmap(BitmapFactory.decodeFile(fileRoute));
		} else {
			btnSend.setVisibility(Button.INVISIBLE);
			tvPhoto.setVisibility(TextView.INVISIBLE);
			iv.setVisibility(ImageView.INVISIBLE);
		}
	}

	private class AsyncTaskSubmitPicture extends
			AsyncTask<Integer, SimpleResponse, Void> {

		private ProgressDialog dialog;
		private Context context;
		private Submit sm;
		private int resultCode;
		private User usr;

		public AsyncTaskSubmitPicture(Context cont, User usr) {
			this.context = cont;
			this.resultCode = -1;
			this.usr = usr;
		}

		@Override
		protected void onPreExecute() {
			try {
				this.dialog = ProgressDialog.show(context,
						context.getString(R.string.cargando),
						context.getString(R.string.sendToServer), true);
			} catch (Exception e) {
				Log.e("asyncTaskSubmitPicture",
						"Error en preExecute de AsyncTask");
			}
		}

		@Override
		protected Void doInBackground(Integer... idPlaces) {
			String auth_token = usr.getAuthKey();
			int idPlace = idPlaces[0].intValue();
			EditText etTitle = (EditText) findViewById(R.id.etPhotoTitle);
			String title = etTitle.getText().toString();

			if (fileRoute != "") {
				try {
					sm = new Submit(GlobalParameters.BASE_URL_JSON);
					SimpleResponse sr = sm.picture(String.valueOf(idPlace),
							title, "desc", fileRoute, GlobalParameters.API_KEY,
							auth_token);
					publishProgress(sr);
				} catch (Exception e) {
					Log.e("asyncTaskSubmitPicture", "Error al enviar la imagen");
					return null;
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(SimpleResponse... responses) {
			SimpleResponse resp = responses[0];
			if (resp == null || resp.getStatus() != 0) {
				Toast.makeText(
						context,
						"El servidor no reconoce la imagen o ha fallado al subirla",
						Toast.LENGTH_SHORT);
			} else {
				resultCode = 1;
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			try {
				dialog.dismiss();

				if (resultCode > 0) {
					TwitterJ twitter = new TwitterJ(getApplicationContext());
					if (twitter.hasAccessToken()) {
						twitter.newTweet("He subido una foto de " + placeName
								+ " " + GlobalParameters.TWITTER_HASHTAG);
					}

					Toast.makeText(getApplicationContext(),
							"Foto subida correctamente!", Toast.LENGTH_SHORT)
							.show();

					fileRoute = "";
					savePreferences();
					showImage();

					setResult(RESULT_OK);
					TakePhoto.this.finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Error al subir la imagen", Toast.LENGTH_SHORT)
							.show();
					TakePhoto.this.finish();
				}
			} catch (Exception e) {
				Log.e("asyncTaskSubmitPicture", "Error al cerrar el dialog");
			}
		}
	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void onActionBarHomeButtonClick(View v) {

		startActivity(new Intent(this, DashboardActivity.class));
	}
}
