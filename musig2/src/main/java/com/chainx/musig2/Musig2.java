package com.chainx.musig2;

import android.text.TextUtils;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Musig2 {
    public static Pointer getMyKeypair(String priv) {
        return clib.get_my_keypair(priv);
    };

    public static String getMyPubkey(Pointer keypair) {
        return clib.get_my_pubkey(keypair);
    };

    public static String getAggKey(String[] pubkeys) {
        return clib.get_key_agg(TextUtils.join("", pubkeys).toString());
    };

    public static Pointer getRound1State(Pointer keypair) {
        return clib.get_round1_state(keypair);
    };

    public static String getRound1Msg(String round1State) {
        return clib.get_round1_msg(round1State);
    };

    public static Pointer getRound2State(Pointer round1State, String msg, String myPubkey, String[] pubkeys, String[] receivedRound1Msg){
        return clib.get_round2_state(round1State, msg, myPubkey, TextUtils.join("", pubkeys).toString(), TextUtils.join("", receivedRound1Msg).toString());
    };

    public static String getRound2R(Pointer round2State) {
        return clib.get_round2_r(round2State);
    };

    public static String getRound2Msg(Pointer round2State) {
        return clib.get_round2_msg(round2State);
    };

    public static String getSignature(Pointer round2State, String[] receivedRound2Msg, String R) {
        return clib.get_signature(round2State, TextUtils.join("", receivedRound2Msg).toString(), R);
    };

    final static CLibrary clib = (CLibrary) Native.load(
            "musig2_dll",
            CLibrary.class);
}
