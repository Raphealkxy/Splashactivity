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

public class third_register extends Activity implements View.OnClickListener {

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
    private final String TAG=third_register.class.getSimpleName();
    private TextView mShowRegFbkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化用户连接
        SpeechUtility.createUtility(this, "appid=59571e7f");

        //
        setContentView(R.layout.activity_third_register);
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
        register_bn2=(ImageButton) findViewById(R.id.register_button_backward);
        edit_user_rec= (EditText) findViewById(R.id.user_input_rec);
        editText_pass_rec= (EditText) findViewById(R.id.editText_recpass);
         passrec_btn= (Button) findViewById(R.id.passrec_btn);
        passres_btn= (Button) findViewById(R.id.passres_btn);
        showDupas= (TextView) findViewById(R.id.showDuPaw);
        showsyjb= (TextView) findViewById(R.id.showsyjb);
        mShowRegFbkTextView= (TextView) findViewById(R.id.mShowRegFbkTextView);
        register_bn2.setOnClickListener(this);
        passres_btn.setOnClickListener(this);
        passrec_btn.setOnClickListener(this);
        mToast = Toast.makeText(third_register.this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register_button_backward:
                finish();
                break;
            case R.id.passrec_btn:
                reset();
                getpassword();
                break;
            case R.id.passres_btn:
                register();
                break;
            default:
                break;


        }
    }

    private void register() {
        // 清空参数
        AUTH_ID=edit_user_rec.getText().toString();
        if(AUTH_ID.equals(""))
              {
                  showTip("输入的用户名不能为空");
                  return;
              }
        mVerify.setParameter(SpeechConstant.PARAMS, null);
        mVerify.setParameter(SpeechConstant.ISV_AUDIO_PATH,
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/test.pcm");
        // 对于某些麦克风非常灵敏的机器，如nexus、samsung i9300等，建议加上以下设置对录音进行消噪处理
//			mVerify.setParameter(SpeechConstant.AUDIO_SOURCE, "" + MediaRecorder.AudioSource.VOICE_RECOGNITION);
        if (pwdType == PWD_TYPE_TEXT) {
            // 文本密码注册需要传入密码
            if (TextUtils.isEmpty(mTextPwd)) {
                showTip("请获取密码后进行操作");
                return;
            }
            mVerify.setParameter(SpeechConstant.ISV_PWD, mTextPwd);
            showDupas.setText("请读出：" + mTextPwd);
            showsyjb.setText("训练 第" + 1 + "遍，剩余4遍");
        } else if (pwdType == PWD_TYPE_NUM) {
            // 数字密码注册需要传入密码
            if (TextUtils.isEmpty(mNumPwd)) {
                showTip("请获取密码后进行操作");
                return;
            }
            mVerify.setParameter(SpeechConstant.ISV_PWD, mNumPwd);
            ((TextView) findViewById(R.id.showDuPaw)).setText("请读出："
                    + mNumPwd.substring(0, 8));
            showsyjb.setText("训练 第" + 1 + "遍，剩余4遍");
        }

        //     setRadioClickable(false);
        // 设置auth_id，不能设置为空
        mVerify.setParameter(SpeechConstant.AUTH_ID, AUTH_ID);
        // 设置业务类型为注册
        mVerify.setParameter(SpeechConstant.ISV_SST, "train");
        // 设置声纹密码类型
        mVerify.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
        // 开始注册
        mVerify.startListening(mRegisterListener);
    }

    private VerifierListener mRegisterListener = new VerifierListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onResult(VerifierResult result) {
            ((TextView)findViewById(R.id.showDuPaw)).setText(result.source);

            if (result.ret == ErrorCode.SUCCESS) {
                switch (result.err) {
                    case VerifierResult.MSS_ERROR_IVP_GENERAL:
                        showDupas.setText("内核异常");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_EXTRA_RGN_SOPPORT:
                        mShowRegFbkTextView.setText("训练达到最大次数");
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
                        mShowRegFbkTextView.setText("训练失败，您所读的文本不一致");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TOO_LOW:
                        mShowRegFbkTextView.setText("音量太低");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_NO_ENOUGH_AUDIO:
                        showDupas.setText("音频长达不到自由说的要求");
                    default:
                        mShowRegFbkTextView.setText("");
                        break;
                }

                if (result.suc == result.rgn) {
                    // setRadioClickable(true);
                    showDupas.setText("注册成功");

                    if (PWD_TYPE_TEXT == pwdType) {
                     //   ed_input.setText("您的声纹ID：\n" + result.vid);
                    } else if (PWD_TYPE_NUM == pwdType) {
                     //   ed_input.setText("您的数字密码声纹ID：\n" + result.vid);
                    }

                } else {
                    int nowTimes = result.suc + 1;
                    int leftTimes = result.rgn - nowTimes;

                    if (PWD_TYPE_TEXT == pwdType) {
                        showDupas.setText("请读出：" + mTextPwd);
                    } else if (PWD_TYPE_NUM == pwdType) {
                        showDupas.setText("请读出：" + mNumPwdSegs[nowTimes - 1]);
                    }
                    showsyjb.setText("训练 第" + nowTimes + "遍，剩余" + leftTimes + "遍");
                }

            }else {
                //   setRadioClickable(true);

                showDupas.setText("注册失败，请重新开始。");
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
            // setRadioClickable(true);

            if (error.getErrorCode() == ErrorCode.MSP_ERROR_ALREADY_EXIST) {
                showTip("模型已存在，如需重新注册，请先删除");
            } else {
                showTip("onError Code：" + error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
//            showTip("开始说话");
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
                        mTextPwdSelectDialog = new AlertDialog.Builder(third_register.this)
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

}
