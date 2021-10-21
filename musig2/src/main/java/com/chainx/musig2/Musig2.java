package com.chainx.musig2;

import android.text.TextUtils;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Musig2 {
    public static String getMyPubkey(String priv) {
        return clib.get_my_pubkey(priv);
    };

    public static String getAggPublicKey(String[] pubkeys) {
        return clib.get_key_agg(TextUtils.join("", pubkeys).toString());
    };

    public static Pointer getRound1State() {
        return clib.get_round1_state();
    };

    public static String encodeRound1State(Pointer round1State) {
        return clib.encode_round1_state(round1State);
    };

    public static Pointer decodeRound1State(String round1State) {
        return clib.decode_round1_state(round1State);
    };

    public static String getRound1Msg(Pointer round1State) {
        return clib.get_round1_msg(round1State);
    };

    public static String getRound2Msg(Pointer round1State, String msg, String privkey, String[] pubkeys, String[] receivedRound1Msg){
        return clib.get_round2_msg(round1State, msg, privkey, TextUtils.join("", pubkeys).toString(), TextUtils.join("", receivedRound1Msg).toString());
    };

    public static String getAggSignature(String[] receivedRound2Msg) {
        return clib.get_signature(TextUtils.join("", receivedRound2Msg).toString());
    };

    final static CLibrary clib = (CLibrary) Native.load(
            "musig2_dll",
            CLibrary.class);
}
