package com.android.camera.module.encoder;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.MediaEncoder.MediaEncoderListener;
import java.io.IOException;

public class MediaAudioEncoder extends MediaEncoder {
    private static final int[] AUDIO_SOURCES = new int[]{1, 0, 5};
    private static final int BIT_RATE = 64000;
    public static final int FRAMES_PER_BUFFER = 25;
    private static final String MIME_TYPE = "audio/mp4a-latm";
    public static final int SAMPLES_PER_FRAME = 1024;
    private static final int SAMPLE_RATE = 44100;
    private static final String TAG = MediaAudioEncoder.class.getSimpleName();
    private AudioThread mAudioThread = null;
    protected final Object mMediaCodecLock = new Object();

    private class AudioThread extends Thread {
        private AudioRecord audioRecord;

        public AudioThread(AudioRecord audioRecord) {
            this.audioRecord = audioRecord;
        }

        /* JADX WARNING: Missing block: B:13:?, code:
            r7.this$0.frameAvailableSoon();
     */
        /* JADX WARNING: Missing block: B:15:?, code:
            r7.audioRecord.stop();
     */
        /* JADX WARNING: Missing block: B:19:?, code:
            r1.clear();
            r2 = r7.audioRecord.read(r1, 1024);
     */
        /* JADX WARNING: Missing block: B:20:0x003b, code:
            if (r2 <= 0) goto L_0x0063;
     */
        /* JADX WARNING: Missing block: B:21:0x003d, code:
            r1.position(r2);
            r1.flip();
            r3 = r7.this$0.mMediaCodecLock;
     */
        /* JADX WARNING: Missing block: B:22:0x0047, code:
            monitor-enter(r3);
     */
        /* JADX WARNING: Missing block: B:25:0x004c, code:
            if (r7.this$0.mSkipFrame != false) goto L_0x0059;
     */
        /* JADX WARNING: Missing block: B:26:0x004e, code:
            r7.this$0.encode(r1, r2, r7.this$0.getPTSUs());
     */
        /* JADX WARNING: Missing block: B:27:0x0059, code:
            monitor-exit(r3);
     */
        /* JADX WARNING: Missing block: B:29:?, code:
            r7.this$0.frameAvailableSoon();
     */
        public void run() {
            /*
            r7 = this;
            r0 = -19;
            android.os.Process.setThreadPriority(r0);
            r0 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x007e }
            r0 = r0.mIsCapturing;	 Catch:{ all -> 0x007e }
            if (r0 == 0) goto L_0x006e;
        L_0x000b:
            r0 = com.android.camera.module.encoder.MediaAudioEncoder.TAG;	 Catch:{ all -> 0x007e }
            r1 = "audioThread>>>";
            com.android.camera.log.Log.d(r0, r1);	 Catch:{ all -> 0x007e }
            r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r1 = java.nio.ByteBuffer.allocateDirect(r0);	 Catch:{ all -> 0x007e }
        L_0x001a:
            r2 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0067 }
            r2 = r2.mSync;	 Catch:{ all -> 0x0067 }
            monitor-enter(r2);	 Catch:{ all -> 0x0067 }
            r3 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0064 }
            r3 = r3.mRequestStop;	 Catch:{ all -> 0x0064 }
            if (r3 == 0) goto L_0x0031;
        L_0x0025:
            monitor-exit(r2);	 Catch:{ all -> 0x0064 }
            r0 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0067 }
            r0.frameAvailableSoon();	 Catch:{ all -> 0x0067 }
            r0 = r7.audioRecord;	 Catch:{ all -> 0x007e }
            r0.stop();	 Catch:{ all -> 0x007e }
            goto L_0x006e;
        L_0x0031:
            monitor-exit(r2);	 Catch:{ all -> 0x0064 }
            r1.clear();	 Catch:{ all -> 0x0067 }
            r2 = r7.audioRecord;	 Catch:{ all -> 0x0067 }
            r2 = r2.read(r1, r0);	 Catch:{ all -> 0x0067 }
            if (r2 <= 0) goto L_0x0063;
        L_0x003d:
            r1.position(r2);	 Catch:{ all -> 0x0067 }
            r1.flip();	 Catch:{ all -> 0x0067 }
            r3 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0067 }
            r3 = r3.mMediaCodecLock;	 Catch:{ all -> 0x0067 }
            monitor-enter(r3);	 Catch:{ all -> 0x0067 }
            r4 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0060 }
            r4 = r4.mSkipFrame;	 Catch:{ all -> 0x0060 }
            if (r4 != 0) goto L_0x0059;
        L_0x004e:
            r4 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0060 }
            r5 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0060 }
            r5 = r5.getPTSUs();	 Catch:{ all -> 0x0060 }
            r4.encode(r1, r2, r5);	 Catch:{ all -> 0x0060 }
        L_0x0059:
            monitor-exit(r3);	 Catch:{ all -> 0x0060 }
            r2 = com.android.camera.module.encoder.MediaAudioEncoder.this;	 Catch:{ all -> 0x0067 }
            r2.frameAvailableSoon();	 Catch:{ all -> 0x0067 }
            goto L_0x0063;
        L_0x0060:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0060 }
            throw r0;	 Catch:{ all -> 0x0067 }
        L_0x0063:
            goto L_0x001a;
        L_0x0064:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0064 }
            throw r0;	 Catch:{ all -> 0x0067 }
        L_0x0067:
            r0 = move-exception;
            r1 = r7.audioRecord;	 Catch:{ all -> 0x007e }
            r1.stop();	 Catch:{ all -> 0x007e }
            throw r0;	 Catch:{ all -> 0x007e }
        L_0x006e:
            r0 = r7.audioRecord;
            r0.release();
            r0 = com.android.camera.module.encoder.MediaAudioEncoder.TAG;
            r1 = "audioThread<<<";
            com.android.camera.log.Log.d(r0, r1);
            return;
        L_0x007e:
            r0 = move-exception;
            r1 = r7.audioRecord;
            r1.release();
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.camera.module.encoder.MediaAudioEncoder.AudioThread.run():void");
        }
    }

    public MediaAudioEncoder(MediaMuxerWrapper mediaMuxerWrapper, MediaEncoderListener mediaEncoderListener) {
        super(mediaMuxerWrapper, mediaEncoderListener);
    }

    protected void prepare() throws IOException {
        Log.v(TAG, "prepare>>>");
        this.mTrackIndex = -1;
        this.mMuxerStarted = false;
        this.mIsEOS = false;
        MediaCodecInfo selectAudioCodec = selectAudioCodec(MIME_TYPE);
        if (selectAudioCodec == null) {
            Log.e(TAG, "no appropriate codec for audio/mp4a-latm");
            return;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("selected codec: ");
        stringBuilder.append(selectAudioCodec.getName());
        Log.d(str, stringBuilder.toString());
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(MIME_TYPE, SAMPLE_RATE, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("channel-mask", 16);
        createAudioFormat.setInteger("bitrate", BIT_RATE);
        createAudioFormat.setInteger("channel-count", 1);
        str = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("format: ");
        stringBuilder2.append(createAudioFormat);
        Log.d(str, stringBuilder2.toString());
        this.mMediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
        this.mMediaCodec.configure(createAudioFormat, null, null, 1);
        this.mMediaCodec.start();
        if (this.mListener != null) {
            this.mListener.onPrepared(this);
        }
        Log.v(TAG, "prepare<<<");
    }

    protected boolean startRecording(long j) {
        boolean startRecording = super.startRecording(j);
        if (!startRecording) {
            return false;
        }
        if (this.mAudioThread == null) {
            AudioRecord initAudioRecord = initAudioRecord();
            if (initAudioRecord == null) {
                Log.e(TAG, "failed to initialize AudioRecord");
                return false;
            }
            try {
                initAudioRecord.startRecording();
                if (3 == initAudioRecord.getRecordingState()) {
                    this.mAudioThread = new AudioThread(initAudioRecord);
                    this.mAudioThread.start();
                } else {
                    startRecording = false;
                }
            } catch (Throwable e) {
                Log.e(TAG, e.getMessage(), e);
                startRecording = false;
            }
            if (!(startRecording || initAudioRecord == null)) {
                initAudioRecord.stop();
                initAudioRecord.release();
            }
        }
        return startRecording;
    }

    protected void release() {
        this.mAudioThread = null;
        synchronized (this.mMediaCodecLock) {
            super.release();
        }
    }

    private AudioRecord initAudioRecord() {
        int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, 16, 2);
        int i = 25600;
        if (25600 < minBufferSize) {
            i = (((minBufferSize / 1024) + 1) * 1024) * 2;
        }
        AudioRecord audioRecord = null;
        for (int audioRecord2 : AUDIO_SOURCES) {
            audioRecord = new AudioRecord(audioRecord2, SAMPLE_RATE, 16, 2, i);
            if (audioRecord.getState() != 1) {
                audioRecord.release();
                audioRecord = null;
            } else {
                audioRecord = audioRecord;
            }
            if (audioRecord != null) {
                break;
            }
        }
        return audioRecord;
    }

    private static MediaCodecInfo selectAudioCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (String equalsIgnoreCase : supportedTypes) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }
}
