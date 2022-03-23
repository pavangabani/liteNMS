package com.motadata.kernel.bean;

public class PollingPingBean
{
    Integer packetLoss;

    Integer RTT;

    Integer sentPacket;

    Integer receivePacket;

    public Integer getPacketLoss()
    {
        return packetLoss;
    }

    public void setPacketLoss(Integer packetLoss)
    {
        this.packetLoss = packetLoss;
    }

    public Integer getRTT()
    {
        return RTT;
    }

    public void setRTT(Integer RTT)
    {
        this.RTT = RTT;
    }

    public Integer getSentPacket()
    {
        return sentPacket;
    }

    public void setSentPacket(Integer sentPacket)
    {
        this.sentPacket = sentPacket;
    }

    public Integer getReceivePacket()
    {
        return receivePacket;
    }

    public void setReceivePacket(Integer receivePacket)
    {
        this.receivePacket = receivePacket;
    }
}
