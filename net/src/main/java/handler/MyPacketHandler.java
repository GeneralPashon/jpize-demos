package handler;

import jpize.net.tcp.packet.PacketHandler;
import packet.EncodePacket;
import packet.MessagePacket;
import packet.PingPacket;

public interface MyPacketHandler extends PacketHandler{

    void encode(EncodePacket packet);

    void message(MessagePacket packet);

    void ping(PingPacket packet);

}
