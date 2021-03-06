package com.ss.android.vesdk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.ss.android.medialib.TTRecorder;
import com.ss.android.medialib.camera.TextureHolder;
import com.ss.android.medialib.camera.TextureHolder.Texture;
import com.ss.android.medialib.listener.GLCallback;
import com.ss.android.medialib.listener.NativeInitListener;
import com.ss.android.medialib.model.TimeSpeedModel;
import com.ss.android.vesdk.VEListener.VERecorderStateListener;
import com.ss.android.vesdk.VEPreviewSettings.CAMERA_FACING_ID;
import java.util.List;

public class RecordV1 implements IRecorder {
    private VEAudioEncodeSettings mAudioEncodeSettings = null;
    private Context mContext;
    private VEPreviewSettings mPreviewSettings = null;
    private TTRecorder mRecorder;
    private VERecorderStateListener mRecorderStateCallback;
    private IRenderCallback mRenderCallback;
    private Texture mTexture;
    private TextureHolder mTextureHolder;
    private VEVideoEncodeSettings mVideoEncodeSettings = null;

    public RecordV1(Context context) {
        this.mContext = context;
        this.mTextureHolder = new TextureHolder();
        this.mRecorder = new TTRecorder(context);
        this.mRecorder.setGLCallback(new GLCallback() {
            public Texture onCreateTexture() {
                RecordV1.this.mTexture = RecordV1.this.mRenderCallback != null ? RecordV1.this.mRenderCallback.onCreateTexture() : null;
                if (RecordV1.this.mTexture == null) {
                    return null;
                }
                return new Texture(RecordV1.this.mTexture.getSurfaceTextureID(), RecordV1.this.mTexture.getSurfaceTexture());
            }

            public void onTextureCreated(Texture texture) {
                if (RecordV1.this.mRenderCallback != null) {
                    if (RecordV1.this.mTexture == null) {
                        RecordV1.this.mTexture = new Texture(texture.getSurfaceTextureID(), texture.getSurfaceTexture());
                    }
                    RecordV1.this.mRenderCallback.onTextureCreated(RecordV1.this.mTexture);
                }
            }

            public boolean onDestroy() {
                return RecordV1.this.mRenderCallback != null ? RecordV1.this.mRenderCallback.onDestroy() : true;
            }
        });
        this.mRecorder.setNativeInitListener(new NativeInitListener() {
            public void onNativeInitCallBack(int i) {
                if (RecordV1.this.mRecorderStateCallback != null) {
                    RecordV1.this.mRecorderStateCallback.onNativeInit(i, i == 0 ? "success" : "failed");
                }
            }

            public void onNativeInitHardEncoderRetCallback(int i, int i2) {
                if (RecordV1.this.mRecorderStateCallback != null) {
                    RecordV1.this.mRecorderStateCallback.onHardEncoderInit(i == 0);
                }
            }
        });
    }

    public void setRenderCallback(IRenderCallback iRenderCallback) {
        this.mRenderCallback = iRenderCallback;
    }

    public void setRecordStateCallback(VERecorderStateListener vERecorderStateListener) {
        this.mRecorderStateCallback = vERecorderStateListener;
    }

    public int init(@NonNull VEVideoEncodeSettings vEVideoEncodeSettings, @NonNull VEAudioEncodeSettings vEAudioEncodeSettings, @NonNull VEPreviewSettings vEPreviewSettings, String str, String str2) {
        this.mVideoEncodeSettings = vEVideoEncodeSettings;
        this.mAudioEncodeSettings = vEAudioEncodeSettings;
        this.mPreviewSettings = vEPreviewSettings;
        VESize size = vEPreviewSettings.getSize();
        VESize videoRes = vEVideoEncodeSettings.getVideoRes();
        if (!str2.endsWith("/")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append("/");
            str2 = stringBuilder.toString();
        }
        return this.mRecorder.init(size.width, size.height, str2, videoRes.width, videoRes.height, str);
    }

    public int startPreview(Surface surface) {
        return this.mRecorder.startPreview(surface, this.mPreviewSettings.getRotation(), this.mPreviewSettings.getFacingID() == CAMERA_FACING_ID.FACING_FRONT);
    }

    public int updatePreviewSettings(VEPreviewSettings vEPreviewSettings) {
        boolean z;
        this.mPreviewSettings = vEPreviewSettings;
        TTRecorder tTRecorder = this.mRecorder;
        int rotation = this.mPreviewSettings.getRotation();
        if (this.mPreviewSettings.getFacingID() == CAMERA_FACING_ID.FACING_FRONT) {
            z = true;
        } else {
            z = false;
        }
        tTRecorder.updateCameraInfo(rotation, z);
        return 0;
    }

    public int stopPreview() {
        return this.mRecorder.stopPreview();
    }

    public int setRecordBGM(String str, int i, int i2, int i3) {
        this.mRecorder.setMusicPath(str);
        this.mRecorder.setMusicStart((long) i);
        return 0;
    }

    public int startRecord(String str, String str2, float f) {
        return this.mRecorder.startRecord((double) f, this.mVideoEncodeSettings.isHwEnc() ^ 1, 1.0f, 1, this.mVideoEncodeSettings.getEncodeProfile());
    }

    public int stopRecord() {
        return this.mRecorder.stopRecord();
    }

    public long getEndFrameTime() {
        return this.mRecorder.getEndFrameTime();
    }

    public void deleteLastFrag() {
        this.mRecorder.deleteLastFrag();
    }

    public void clearEnv() {
        this.mRecorder.clearEnv();
    }

    public int concat(String str, String str2) {
        return this.mRecorder.concatSyn(str, str2);
    }

    public List<TimeSpeedModel> getSegmentInfo() {
        return this.mRecorder.getSegmentInfo();
    }

    public int tryRestore() {
        return this.mRecorder.tryRestore();
    }

    public void destroy() {
        this.mRecorder.destroy();
        this.mRecorder = null;
    }

    public int setFilter(String str, float f) {
        return this.mRecorder.setFilter(str, f);
    }

    public int setFilter(String str, String str2, float f) {
        return this.mRecorder.setFilter(str, str2, f);
    }

    public int setBeautyFace(int i, String str) {
        return this.mRecorder.setBeautyFace(i, str);
    }

    public int setBeautyFaceIntensity(float f, float f2) {
        return this.mRecorder.setBeautyFaceIntensity(f, f2);
    }

    public int setFaceReshape(String str, float f, float f2) {
        return this.mRecorder.setFaceReshape(str, f, f2);
    }

    public int switchEffect(String str) {
        return this.mRecorder.setSticker(str);
    }

    public int setDeviceRotation(float[] fArr) {
        return this.mRecorder.setDeviceRotation(fArr);
    }

    public int pauseEffectAudio(boolean z) {
        return z ? this.mRecorder.pauseEffectAudio() : this.mRecorder.resumeEffectAudio();
    }
}
