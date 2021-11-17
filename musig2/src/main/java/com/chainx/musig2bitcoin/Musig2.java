package com.chainx.musig2bitcoin;

import android.text.TextUtils;

public class Musig2 {
    static {
        System.loadLibrary("musig2_dll");
    }
    
    public static native String get_my_pubkey(String jarg1);

    public static native long get_round1_state();

    public static native String encode_round1_state(long jarg1);

    public static native long decode_round1_state(String jarg1);

    public static native String get_round1_msg(long jarg1);

    public static native String get_round2_msg(long jarg1, String jarg2, String jarg3, String jarg4, String jarg5);

    public static native String get_signature(String jarg1);

    public static native String get_key_agg(String jarg1);

    public static String getMyPubkey(String priv) {
        return get_my_pubkey(priv);
    };

    public static String getAggPublicKey(String[] pubkeys) {
        return get_key_agg(TextUtils.join("", pubkeys).toString());
    };

    public static long getRound1State() {
        return get_round1_state();
    };

    public static String encodeRound1State(long round1State) {
        return encode_round1_state(round1State);
    };

    public static long decodeRound1State(String round1State) {
        return decode_round1_state(round1State);
    };

    public static String getRound1Msg(long round1State) {
        return get_round1_msg(round1State);
    };

    public static String getRound2Msg(long round1State, String msg, String privkey, String[] pubkeys, String[] receivedRound1Msg){
        return get_round2_msg(round1State, msg, privkey, TextUtils.join("", pubkeys).toString(), TextUtils.join("", receivedRound1Msg).toString());
    };

    public static String getAggSignature(String[] receivedRound2Msg) {
        return get_signature(TextUtils.join("", receivedRound2Msg).toString());
    };
}
