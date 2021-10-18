package com.chainx.musig2;

import android.text.TextUtils;

import com.sun.jna.Native;

public class Mast {
    public static String generateThresholdPubkey(String[] pubkeys, byte threshold) {
        return clib.generate_threshold_pubkey(TextUtils.join("", pubkeys).toString(), threshold);
    }

    public static String generateControlBlock(String[] pubkeys, byte threshold, String sigAggPubkey) {
        return clib.generate_control_block(TextUtils.join("", pubkeys).toString(), threshold, sigAggPubkey);
    }

    final static CLibrary clib = (CLibrary) Native.load(
            "musig2_dll",
            CLibrary.class);
}
