package com.catlivevideo.living.catlivevideo.rtmp.packets;

import com.catlivevideo.living.catlivevideo.rtmp.io.ChunkStreamInfo;

/**
 * Video data packet
 *  
 * @author francois
 */
public class Video extends ContentData {

    public Video(RtmpHeader header) {
        super(header);
    }

    public Video() {
        super(new RtmpHeader(RtmpHeader.ChunkType.TYPE_0_FULL, ChunkStreamInfo.RTMP_VIDEO_CHANNEL, RtmpHeader.MessageType.VIDEO));
    }

    @Override
    public String toString() {
        return "RTMP Video";
    }
}
