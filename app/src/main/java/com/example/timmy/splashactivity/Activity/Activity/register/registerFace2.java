package com.example.timmy.splashactivity.Activity.Activity.register;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.Activity.Activity.utils.FaceUtil;
import com.example.timmy.splashactivity.Activity.Activity.utils.ToastUtils;
import com.example.timmy.splashactivity.R;
import com.timmy.data.UrlUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


@ContentView(R.layout.activity_register_face2)
public class registerFace2 extends Activity{


    private TextView tip;
    private ImageView imageView;
    private Toast mToast;
    private final int REQUEST_PICTURE_CHOOSE = 1;//照片选择请求码
    private Bitmap mImage = null;//处理后的位图,也是处理图片的开始文件
    private final int REQUEST_CAMERA_IMAGE = 2;//拍照请求码
    private byte[] mImageData = null;
    private File mPictureFile;
    private String username="";
    private String ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        init();

    }

    private void init() {

        tip= (TextView) findViewById(R.id.tip2);
        imageView= (ImageView) findViewById(R.id.image_pic2);
       // submit.setOnClickListener(this);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        Intent intent = getIntent();
        ID=intent.getStringExtra("Id");
        username=intent.getStringExtra("username");

    }



    @Event(value=R.id.take_photo2)
    private void take_photo(View view) {
        // 设置相机拍照后照片保存路径
        mPictureFile = new File(Environment.getExternalStorageDirectory(),
                "picture" + System.currentTimeMillis()/1000 + ".jpg");
        // 启动拍照,并保存到临时文件
        Intent mIntent = new Intent();
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPictureFile));
        mIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        startActivityForResult(mIntent, REQUEST_CAMERA_IMAGE);
    }

    @Event(value = R.id.btn_getImage2)
    private void getImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQUEST_PICTURE_CHOOSE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        String fileSrc = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICTURE_CHOOSE) {
            if ("file".equals(data.getData().getScheme())) {
                // 有些低版本机型返回的Uri模式为file
                fileSrc = data.getData().getPath();
            } else {
                // Uri模型为content
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(data.getData(), proj,
                        null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                fileSrc = cursor.getString(idx);
                cursor.close();
            }
            // 跳转到图片裁剪页面
            FaceUtil.cropPicture(this, Uri.fromFile(new File(fileSrc)));
        } else if (requestCode == REQUEST_CAMERA_IMAGE) {
            if (null == mPictureFile) {
                ToastUtils.show(registerFace2.this,"拍照失败，请重试",2);
                return;
            }

            fileSrc = mPictureFile.getAbsolutePath();
            updateGallery(fileSrc);
            // 跳转到图片裁剪页面
            FaceUtil.cropPicture(this,Uri.fromFile(new File(fileSrc)));
        } else if (requestCode == FaceUtil.REQUEST_CROP_IMAGE) {
            // 获取返回数据
            Bitmap bmp = data.getParcelableExtra("data");
            // 若返回数据不为null，保存至本地，防止裁剪时未能正常保存
            if(null != bmp){
                FaceUtil.saveBitmapToFile(registerFace2.this, bmp);
            }
            // 获取图片保存路径
            fileSrc = FaceUtil.getImagePath(registerFace2.this);
            //       showTip(fileSrc);
            // 获取图片的宽和高
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            mImage = BitmapFactory.decodeFile(fileSrc, options);


            if(null == mImage) {
                ToastUtils.show(registerFace2.this,"图片信息无法正常获取！",2);

                return;
            }

            final String finalFileSrc = fileSrc;
            Map<String, String> params = new HashMap<>();
            params.put("ID", ID);
            params.put("username",username);
            NetRequest netRequest=new NetRequest(registerFace2.this, UrlUtils.NET_REGISTERFACE,params,2,finalFileSrc,2,ID);
            netRequest.handlerResult = new registerFace2.myHandlerResult();
            netRequest.execute();
            imageView.setImageBitmap(mImage);
        }





    }

    public class myHandlerResult extends HandlerResult {

        @Override
        public void success() {
            Toast.makeText(registerFace2.this, "人脸注册成功", Toast.LENGTH_SHORT).show();
            direct();
        }

        @Override
        public void failed() {
            ToastUtils.show(registerFace2.this,"人脸注册失败请重新上传照片",2);
        }
    }


    private void updateGallery(String filename) {
        MediaScannerConnection.scanFile(this, new String[] {filename}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }




    private void direct() {
        Intent intent=new Intent(this,registerFace3.class);
        intent.putExtra("Id",ID);
        intent.putExtra("username",username);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }
}
