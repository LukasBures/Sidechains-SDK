package com.horizen.transaction.mainchain;

import com.horizen.block.MainchainTxForwardTransferCrosschainOutput;
import com.horizen.box.RegularBox;
import scala.util.Success;
import scala.util.Try;

import java.util.Arrays;

public final class ForwardTransfer implements SidechainRelatedMainchainOutput<RegularBox> {

    private MainchainTxForwardTransferCrosschainOutput _output;

    public ForwardTransfer(MainchainTxForwardTransferCrosschainOutput output) {
        _output = output;
    }
    @Override
    public byte[] hash() {
        return _output.hash();
    }

    // DO TO: detect SC related addresses (PublicKey25519Proposition) and values, create RegularBoxes for them.
    @Override
    public RegularBox getBox(long nonce) {
        return new RegularBox(_output.proposition(), nonce, _output.amount());
    }

    @Override
    public byte[] bytes() {
        return Arrays.copyOf(_output.forwardTransferOutputBytes(), _output.forwardTransferOutputBytes().length);
    }

    public static Try<ForwardTransfer> parseBytes(byte[] bytes) {
        MainchainTxForwardTransferCrosschainOutput output = MainchainTxForwardTransferCrosschainOutput.create(bytes, 0).get();
        return new Success<>(new ForwardTransfer(output));
    }

    @Override
    public SidechainRelatedMainchainOutputSerializer serializer() {
        return ForwardTransferSerializer.getSerializer();
    }
}
