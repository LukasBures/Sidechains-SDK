package com.horizen.box;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import com.horizen.box.data.ForgerBoxData;
import com.horizen.box.data.ForgerBoxDataSerializer;
import com.horizen.proposition.PublicKey25519Proposition;
import com.horizen.proposition.VrfPublicKey;

import java.util.Arrays;

import static com.horizen.box.CoreBoxesIdsEnum.ForgerBoxId;

public final class ForgerBox
        extends AbstractNoncedBox<PublicKey25519Proposition, ForgerBoxData, ForgerBox>
        implements CoinsBox<PublicKey25519Proposition>
{

    public ForgerBox(ForgerBoxData boxData, long nonce) {
        super(boxData, nonce);
    }

    @Override
    public byte boxTypeId() {
        return ForgerBoxId.id();
    }

    public VrfPublicKey vrfPubKey() {
        return boxData.vrfPublicKey();
    }

    @JsonProperty("vrfPubKey")
    public byte[] vrfPubKeyBytes() {
        return boxData.vrfPublicKey().bytes();
    }

    @JsonProperty("rewardProposition")
    public PublicKey25519Proposition rewardProposition() {
        return boxData.rewardProposition();
    }

    @Override
    public byte[] bytes() {
        return Bytes.concat(Longs.toByteArray(nonce), ForgerBoxDataSerializer.getSerializer().toBytes(boxData));
    }

    @Override
    public BoxSerializer serializer() {
        return ForgerBoxSerializer.getSerializer();
    }

    @Override
    public String toString() {
        return String.format("%s(id: %s, proposition: %s, value: %d, vrfPubKey: %s, rewardProposition: %s, nonce: %d)", this.getClass().toString(), encoder().encode(id()), proposition(), value(), vrfPubKey(), rewardProposition(), nonce());
    }

    public static ForgerBox parseBytes(byte[] bytes) {
        long nonce = Longs.fromByteArray(Arrays.copyOf(bytes, Longs.BYTES));

        int forgerBoxDataOffset = Longs.BYTES;
        ForgerBoxData boxData = ForgerBoxDataSerializer.getSerializer().parseBytes(Arrays.copyOfRange(bytes, forgerBoxDataOffset, bytes.length));

        return boxData.getBox(nonce);
    }
}
