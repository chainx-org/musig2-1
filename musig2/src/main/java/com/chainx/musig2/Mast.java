package com.chainx.musig2;

import android.text.TextUtils;

public class Mast {
    static {
        System.loadLibrary("musig2_dll");
    }

    public static native String generate_threshold_pubkey(String jarg1, long jarg2);

    public static native String generate_control_block(String jarg1, long jarg2, String jarg3);

    public static String generateThresholdPubkey(String[] pubkeys, byte threshold) {
        return generate_threshold_pubkey(TextUtils.join("", pubkeys).toString(), threshold);
    }

    public static String generateControlBlock(String[] pubkeys, byte threshold, String sigAggPubkey) {
        return generate_control_block(TextUtils.join("", pubkeys).toString(), threshold, sigAggPubkey);
    }
}
