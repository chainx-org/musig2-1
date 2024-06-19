package com.chainx.musig2bitcoin;

public class Transaction {
    static {
        System.loadLibrary("musig2_dll");
    }

    public static native String get_base_tx(String prev_tx, String txid, long index);

    public static native String add_input(String base_tx, String prev_tx, String txid, long index);

    public static native String add_output(String base_tx, String address, long amount);

    public static native String get_sighash(String base_tx,
                                            String txid,
                                            long input_index,
                                            String agg_pubkey,
                                            long sigversion,
                                            String protocol);

    public static native String get_unsigned_tx(String base_tx);

    public static native String build_raw_script_tx(String base_tx,
                                                    String agg_signature,
                                                    String agg_pubkey,
                                                    String control,
                                                    String txid,
                                                    long input_index
                                                    String protocol);

    public static native String build_raw_key_tx(String base_tx, String signature, String txid, long input_index);

    public static native String generate_schnorr_signature(String message, String privkey);

    public static native String get_script_pubkey(String addr);

    public static native String get_spent_outputs(String prev_tx, long input_index);

    public static native String add_spent_output(String spent_outputs, String prev_tx, long input_index);

    public static native String generate_btc_address(String pubkey, String network);

    public static String generateSchnorrSignature(String message, String privkey) {
        return generate_schnorr_signature(message, privkey);
    }

    public static String getScriptPubkey(String addr) {
        return get_script_pubkey(addr);
    }

    public static String generateRawTx(String[] prev_txs, String[] txids, long[] input_indexs, String[] addresses, long[] amounts) {
        if (txids.length != input_indexs.length) {
            return "txids and indexs must be equal in length";
        }
        if (txids.length != prev_txs.length) {
            return "txids and prev_txs must be equal in length";
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

        String base_tx = get_base_tx(prev_txs[0], txids[0], input_indexs[0]);
        for (int i = 1; i < txids.length; i++) {
            base_tx = add_input(base_tx, prev_txs[i], txids[i], input_indexs[i]);
        }
        for (int i = 0; i < addresses.length; i++) {
            base_tx = add_output(base_tx, addresses[i], amounts[i]);
        }

        return base_tx;
    }

    public static String getSighash(String tx, String txid, long input_index, String agg_pubkey, long sigversion, String protocol) {
        return get_sighash(tx, txid, input_index, agg_pubkey, sigversion, protocol);
    }

    public static String getUnsignedTx(String tx) {
        return get_unsigned_tx(tx);
    }

    public static String buildThresholdTx(String tx, String agg_signature, String agg_pubkey, String control, String txid, long input_index, String protocol) {
        return build_raw_script_tx(tx, agg_signature, agg_pubkey, control, txid, input_index, protocol);
    }

    public static String buildTaprootTx(String tx, String signature, String txid, long input_index) {
        return build_raw_key_tx(tx, signature, txid, input_index);
    }

    public static String generateSpentOutputs(String[] prev_txs, long[] input_indexs) {
        if (prev_txs.length != input_indexs.length) {
            return "prev_txs and indexs must be equal in length";
        }
        if (prev_txs.length == 0 || input_indexs.length == 0) {
            return "prev_txs count must be greater than 0";
        }

        String spent_outputs = get_spent_outputs(prev_txs[0], input_indexs[0]);
        for (int i = 1; i < prev_txs.length; i++) {
            spent_outputs = add_spent_output(spent_outputs, prev_txs[i], input_indexs[i]);
        }
        return spent_outputs;
    }

    public static String getMyAddress(String pubkey, String network) {
        return generate_btc_address(pubkey, network);
    }
}
