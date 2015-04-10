//package net.ibaixin.chat.activity;
//
//import java.util.List;
//import net.ibaixin.chat.R;
//import net.ibaixin.chat.util.Observable;
//import net.ibaixin.chat.util.Observer;
//import android.annotation.TargetApi;
//import android.content.ContentUris;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.provider.MediaStore.MediaColumns;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
///**
// * 
// * @author huanghui1
// * @version 1.0.0
// * @update 2015年1月31日 上午11:50:55
// */
//public class TestActivity extends BaseActivity implements Observer, View.OnClickListener {
//	private ListView listView;
//	private List<String> data;
//	private ArrayAdapter<String> adapter;
//	
//	private EditText etContent;
//	private Button btnChoiceFile;
//	private Button btnUpload;
//
//	@Override
//	protected int getContentView() {
//		return R.layout.activity_test;
//	}
//
//	@Override
//	protected void initView() {
////		listView = (ListView) findViewById(R.id.lv_data);
//		
//		etContent = (EditText) findViewById(R.id.et_content);
//		btnChoiceFile = (Button) findViewById(R.id.btn_choice_file);
//		btnUpload = (Button) findViewById(R.id.btn_upload);
//	}
//	
//	@Override
//	protected void initData() {
//		/*DataTestManager.getInstance().addObserver(this);
//		data = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			data.add("test" + i);
//		}
//		adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, data);
//		adapter.registerDataSetObserver(new DataSetObserver() {
//		});
//		listView.setAdapter(adapter);
//		
//		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
//				builder.title("测试标题")
//				.content("测试内容")
//				.positiveText("确定")
//				.negativeText("取消")
//				.show();
//				return true;
//			}
//		});*/
//	}
//	
//	
//	@Override
//	protected void addListener() {
//		btnChoiceFile.setOnClickListener(this);
//		btnUpload.setOnClickListener(this);
//	}
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add(0, 1, 0, "添加");
//		MenuItem menuItem = menu.getItem(0);
//		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//		return super.onCreateOptionsMenu(menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case 1:	//添加
////			DataTestManager.getInstance().add(String.valueOf(new Random().nextInt(9999)));
//			break;
//
//		default:
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
//	@Override
//	protected void onDestroy() {
////		DataTestManager.getInstance().removeObserver(this);
//		super.onDestroy();
//	}
//
//	@Override
//	public void update(Observable<?> observable, int notifyFlag,
//			UpdateType updateType, Object data) {
//		if (updateType == UpdateType.ADD) {
//			this.data.add((String) data);
//			adapter.notifyDataSetChanged();
//			Log.d(TAG, "----------update------updateType----data--" + updateType.toString() + data);
//		}
//	}
//
//	@Override
//	public void dispatchUpdate(Observable<?> observable, int notifyFlag,
//			UpdateType updateType, Object data) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			if (requestCode == 100 && data != null) {
//				String filePath = getPath(mContext, data.getData());
//				etContent.setText(filePath);
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//	public static String getPath(final Context context, final Uri uri) {
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/"
//                            + split[1];
//                }
//                // TODO handle non-primary volumes
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"),
//                        Long.valueOf(id));
//                return getDataColumn(context, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//                final String selection = MediaColumns._ID + "=?";
//                final String[] selectionArgs = new String[] { split[1] };
//                return getDataColumn(context, contentUri, selection,
//                        selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // Return the remote address
//            if (isGooglePhotosUri(uri))
//                return uri.getLastPathSegment();
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    /**
//     * Get the value of the data column for this Uri . This is useful for
//     * MediaStore Uris , and other file - based ContentProviders.
//     * 
//     * @param context
//     *            The context.
//     * @param uri
//     *            The Uri to query.
//     * @param selection
//     *            (Optional) Filter used in the query.
//     * @param selectionArgs
//     *            (Optional) Selection arguments used in the query.
//     * @return The value of the _data column, which is typically a file path.
//     */
//    public static String getDataColumn(Context context, Uri uri,
//            String selection, String[] selectionArgs) {
//        Cursor cursor = null;
//        final String column = MediaColumns.DATA;
//        final String[] projection = { column };
//        try {
//            cursor = context.getContentResolver().query(uri, projection,
//                    selection, selectionArgs, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }
//
//    /**
//     * @param uri
//     *            The Uri to check.
//     * @return Whether the Uri authority is ExternalStorageProvider.
//     */
//    public static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri
//                .getAuthority());
//    }
//
//    /**
//     * @param uri
//     *            The Uri to check.
//     * @return Whether the Uri authority is DownloadsProvider.
//     */
//    public static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri
//                .getAuthority());
//    }
//
//    /**
//     * @param uri
//     *            The Uri to check.
//     * @return Whether the Uri authority is MediaProvider.
//     */
//    public static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri
//                .getAuthority());
//    }
//
//    /**
//     * @param uri
//     *            The Uri to check.
//     * @return Whether the Uri authority is Google Photos.
//     */
//    public static boolean isGooglePhotosUri(Uri uri) {
//        return "com.google.android.apps.photos.content".equals(uri
//                .getAuthority());
//    }
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_choice_file:	//选择文件
//			Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//			intent.setType("*/*");
//			startActivityForResult(intent, 100);
//			break;
//		case R.id.btn_upload:	//上传文件
//			break;
//		default:
//			break;
//		}
//	}
//
//}
