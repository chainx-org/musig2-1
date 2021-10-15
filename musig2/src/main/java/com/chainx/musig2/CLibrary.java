package com.chainx.musig2;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface CLibrary extends Library {
    public Pointer get_my_keypair(String priv);

    public String get_my_pubkey(Pointer keypair);

    public String get_key_agg(String pubkeys);

    public Pointer get_round1_state(Pointer keypair);

    public String get_round1_msg(String round1State);

    public Pointer get_round2_state(Pointer round1State, String msg, String myPubkey, String pubkeys, String receivedRound1Msg);

    public String get_round2_r(Pointer round2State);

    public String get_round2_msg(Pointer round2State);

    public String get_signature(Pointer round2State, String receivedRound2Msg, String R);
}
