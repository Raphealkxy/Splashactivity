package com.example.timmy.splashactivity.Activity.Activity.Isv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeakerVerifier;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechListener;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.VerifierListener;
import com.iflytek.cloud.VerifierResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class third_check extends Activity implements View.OnClickListener {
    private ImageButton register_bn2;
    private EditText edit_user_rec;
    private EditText editText_pass_rec;
    private Button passres_btn;
    private Button passrec_btn;


    private SpeakerVerifier mVerify;
    //初始化参数
    private static final int PWD_TYPE_TEXT = 1;
    // 自由说由于效果问题，暂不开放
//	private static final int PWD_TYPE_FREE = 2;
    private static final int PWD_TYPE_NUM = 3;
    // 当前声纹密码类型，1、2、3分别为文本、自由说和数字密码
    private int pwdType = PWD_TYPE_TEXT;
    // 声纹识别对象
    private String AUTH_ID="";//用户ID

    private AlertDialog mTextPwdSelectDialog;
    private String mTextPwd = "";
    // 数字声纹密码
    private String mNumPwd = "";
    private String[] mNumPwdSegs;
    private Toast mToast;
    private TextView showDupas;
    private TextView showsyjb;
    private final String TAG=third_check.class.getSimpleName();
    private TextView mShowRegFbkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_check);
        SpeechUtility.createUtility(this, "appid=59571e7f");

        //
       // setContentView(R.layout.activity_third_check);
        initView();
        //初始化speakverfy
        mVerify = SpeakerVerifier.createVerifier(this, new InitListener() {

            @Override
            public void onInit(int errorCode) {
                if (ErrorCode.SUCCESS == errorCode) {
                    showTip("引擎初始化成功");
                } else {
                    showTip("引擎初始化失败，错误码：" + errorCode);
                }
            }
        });
    }


    private void initView() {
        register_bn2=(ImageButton) findViewById(R.id.check_button_backward);
        edit_user_rec= (EditText) findViewById(R.id.user_input_rec_check);
        editText_pass_rec= (EditText) findViewById(R.id.editText_recpass_check);
        passrec_btn= (Button) findViewById(R.id.passrec_btn_check);
        passres_btn= (Button) findViewById(R.id.btn_check);
        showDupas= (TextView) findViewById(R.id.showDuPaw);
        showsyjb= (TextView) findViewById(R.id.showsyjb);
        mShowRegFbkTextView= (TextView) findViewById(R.id.mShowRegFbkTextView);


         register_bn2.setOnClickListener(this);
        passres_btn.setOnClickListener(this);
        passrec_btn.setOnClickListener(this);
        mToast = Toast.makeText(third_check.this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
    }




    //验证模块函数
    private void checked() {

        AUTH_ID=edit_user_rec.getText().toString();
        if(AUTH_ID.equals(""))
        {
            showTip("输入的用户名不能为空");
            return;
        }
        // 清空提示信息
        ((TextView) findViewById(R.id.showDuPaw)).setText("");
        // 清空参数
        mVerify.setParameter(SpeechConstant.PARAMS, null);
        mVerify.setParameter(SpeechConstant.ISV_AUDIO_PATH,
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/verify.pcm");
        mVerify = SpeakerVerifier.getVerifier();
        // 设置业务类型为验证
        mVerify.setParameter(SpeechConstant.ISV_SST, "verify");
        // 对于某些麦克风非常灵敏的机器，如nexus、samsung i9300等，建议加上以下设置对录音进行消噪处理
//			mVerify.setParameter(SpeechConstant.AUDIO_SOURCE, "" + MediaRecorder.AudioSource.VOICE_RECOGNITION);

        if (pwdType == PWD_TYPE_TEXT) {
            // 文本密码注册需要传入密码
            if (TextUtils.isEmpty(mTextPwd)) {
                showTip("请获取密码后进行操作");
                return;
            }
            mVerify.setParameter(SpeechConstant.ISV_PWD, mTextPwd);
            ((TextView) findViewById(R.id.showDuPaw)).setText("请读出："
                    + mTextPwd);
        } else if (pwdType == PWD_TYPE_NUM) {
            // 数字密码注册需要传入密码
            String verifyPwd = mVerify.generatePassword(8);
            mVerify.setParameter(SpeechConstant.ISV_PWD, verifyPwd);
            ((TextView) findViewById(R.id.showDuPaw)).setText("请读出："
                    + verifyPwd);
        }
        //setRadioClickable(false);
        // 设置auth_id，不能设置为空
        mVerify.setParameter(SpeechConstant.AUTH_ID, AUTH_ID);
        mVerify.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
        // 开始验证
        mVerify.startListening(mVerifyListener);
    }

    private VerifierListener mVerifyListener = new VerifierListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onResult(VerifierResult result) {
            //setRadioClickable(true);
            showDupas.setText(result.source);

            if (result.ret == 0) {
                // 验证通过
                showsyjb.setText("验证通过");
            }
            else{
                // 验证不通过
                switch (result.err) {
                    case VerifierResult.MSS_ERROR_IVP_GENERAL:
                        mShowRegFbkTextView.setText("内核异常");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TRUNCATED:
                        mShowRegFbkTextView.setText("出现截幅");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_MUCH_NOISE:
                        mShowRegFbkTextView.setText("太多噪音");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_UTTER_TOO_SHORT:
                        mShowRegFbkTextView.setText("录音太短");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TEXT_NOT_MATCH:
                        mShowRegFbkTextView.setText("验证不通过，您所读的文本不一致");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TOO_LOW:
                        mShowRegFbkTextView.setText("音量太低");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_NO_ENOUGH_AUDIO:
                        mShowRegFbkTextView.setText("音频长达不到自由说的要求");
                        break;
                    default:
                        mShowRegFbkTextView.setText("验证不通过");
                        break;
                }
            }
        }
        // 保留方法，暂不用
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle arg3) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

        @Override
        public void onError(SpeechError error) {
            //setRadioClickable(true);

            switch (error.getErrorCode()) {
                case ErrorCode.MSP_ERROR_NOT_FOUND:
                    mShowRegFbkTextView.setText("模型不存在，请先注册");
                    break;

                default:
                    showTip("onError Code："	+ error.getPlainDescription(true));
                    break;
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }
    };


    private void reset() {

        editText_pass_rec.setText("");
        showsyjb.setText("");
        showDupas.setText("");
        mShowRegFbkTextView.setText("");

    }

    private void getpassword() {
        // 获取密码之前先终止之前的注册或验证过程
        mVerify.cancel();
        mVerify.setParameter(SpeechConstant.PARAMS, null);
        mVerify.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
        mVerify.getPasswordList(mPwdListenter);
    }

    private String[] items;
    private SpeechListener mPwdListenter = new SpeechListener() {
        @Override
        public void onEvent(int eventType, Bundle params) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // setRadioClickable(true);

            String result = new String(buffer);
            switch (pwdType) {
                case PWD_TYPE_TEXT:
                    try {
                        JSONObject object = new JSONObject(result);
                        if (!object.has("txt_pwd")) {
                            initTextView();
                            return;
                        }

                        JSONArray pwdArray = object.optJSONArray("txt_pwd");
                        items = new String[pwdArray.length()];
                        for (int i = 0; i < pwdArray.length(); i++) {
                            items[i] = pwdArray.getString(i);
                        }
                        mTextPwdSelectDialog = new AlertDialog.Builder(third_check.this)
                                .setTitle("请选择密码文本")
                                .setItems(items,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface arg0, int arg1) {
                                                mTextPwd = items[arg1];
                                                editText_pass_rec.setText(mTextPwd);
                                            }
                                        }).create();
                        mTextPwdSelectDialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case PWD_TYPE_NUM:
                    StringBuffer numberString = new StringBuffer();
                    try {
                        JSONObject object = new JSONObject(result);
                        if (!object.has("num_pwd")) {
                            initTextView();
                            return;
                        }

                        JSONArray pwdArray = object.optJSONArray("num_pwd");
                        numberString.append(pwdArray.get(0));
                        for (int i = 1; i < pwdArray.length(); i++) {
                            numberString.append("-" + pwdArray.get(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mNumPwd = numberString.toString();
                    mNumPwdSegs = mNumPwd.split("-");
                    editText_pass_rec.setText( mNumPwd);
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onCompleted(SpeechError error) {
            // setRadioClickable(true);

            if (null != error && ErrorCode.SUCCESS != error.getErrorCode()) {
                showTip("获取失败：" + error.getErrorCode());
            }
        }
    };

    private void initTextView() {

        editText_pass_rec.setText("");
        showsyjb.setText("");
        showDupas.setText("");
        mShowRegFbkTextView.setText("");

    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    @Override
    public void onClick(View v) {
                switch (v.getId())
        {
            case R.id.check_button_backward:
               finish();
               break;
            case R.id.passrec_btn_check:
                reset();
                getpassword();
                break;
            case R.id.btn_check:
                checked();
                break;
            default:
                break;


        }
    }
}
