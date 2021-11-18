package com.chainx.musig2bitcoin;

public class Transaction {
    static {
        System.loadLibrary("musig2_dll");
    }

    public static native String get_base_tx(String txid, long index);

    public static native String add_input(String base_tx, String txid, long index);

    public static native String add_output(String base_tx, String address, long amount);

    public static native String get_sighash(String prev_tx,
                                            String tx,
                                            long input_index,
                                            String agg_pubkey,
                                            long sigversion);

    public static native String build_raw_scirpt_tx(String base_tx,
                                                    String agg_signature,
                                                    String agg_pubkey,
                                                    String control,
                                                    long input_index);

    public static native String build_raw_key_tx(String base_tx, String signature, long input_index);

    public static native String generate_schnorr_signature(String message, String privkey);

    public static native String get_my_privkey(String phrase, String pd_passphrase);

    public static native String get_scirpt_pubkey(String addr);

    public static native String get_spent_outputs(String prev_tx, long index);

    public static native String add_spent_output(String spent_outputs, String prev_tx, long index);

    public static native String generate_btc_address(String pubkey, String network);

    public static String generateSchnorrSignature(String message, String privkey) {
        return generate_schnorr_signature(message, privkey);
    }

    public static String getScriptPubkey(String addr) {
        return get_scirpt_pubkey(addr);
    }

    public static String generateRawTx(String[] txids, long[] indexs, String[] addresses, long[] amounts){
        if (txids.length != indexs.length) {
            return "txids and indexs must be equal in length";
        }
        if (addresses.length != amounts.length) {
            return "addresses and amounts must be equal in length";
        }
        if (txids.length == 0) {
            return "Input count must be greater than 0";
        }
        if (addresses.length == 0) {
            return "Output count must be greater than 0";
        }

        String base_tx = get_base_tx(txids[0], indexs[0]);
        for (int i = 1; i < txids.length; i++) {
            base_tx = add_input(base_tx, txids[i], indexs[i]);
        }
        for (int i = 0; i < addresses.length; i++) {
            base_tx = add_output(base_tx, addresses[i], amounts[i]);
        }

        return base_tx;
    }

    public static String getSighash(String prev_tx, String tx, long input_index, String agg_pubkey, long sigversion) {
        return get_sighash(prev_tx, tx, input_index, agg_pubkey, sigversion);
    }

    public static String buildThresholdTx(String tx, String agg_signature, String agg_pubkey, String control, long input_index) {
        return build_raw_scirpt_tx(tx, agg_signature, agg_pubkey, control, input_index);
    }

    public static String buildTaprootTx(String tx, String signature, long input_index) {
        return build_raw_key_tx(tx, signature, input_index);
    }

    public static String generateSpentOutputs(String[] prev_txs, long[] indexs) {
        if (prev_txs.length != indexs.length) {
            return "prev_txs and indexs must be equal in length";
        }
        if (prev_txs.length == 0 || indexs.length == 0) {
            return "prev_txs count must be greater than 0";
        }

        String spent_outputs = get_spent_outputs(prev_txs[0], indexs[0]);
        for (int i = 1; i < prev_txs.length; i++) {
            spent_outputs = add_spent_output(spent_outputs, prev_txs[i], indexs[i]);
        }
        return spent_outputs;
    }

    public static String generateBtcAddress(String pubkey, String network) {
        return generate_btc_address(pubkey, network);
    }
}
