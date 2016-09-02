package com.yimayhd.palace.model.guide;

/**
 * Created by xushubing on 2016/9/2.
 */
public class GuideFocusVO {
    private long id;
    private String name;
    private int audioTime;
    private long attId;
    private int isDel;
    private int weights;
    private String audio;


    public long getId() {
        return id;
    }

    public GuideFocusVO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GuideFocusVO setName(String name) {
        this.name = name;
        return this;
    }

    public int getAudioTime() {
        return audioTime;
    }

    public GuideFocusVO setAudioTime(int audioTime) {
        this.audioTime = audioTime;
        return this;
    }

    public long getAttId() {
        return attId;
    }

    public GuideFocusVO setAttId(long attId) {
        this.attId = attId;
        return this;
    }

    public int getIsDel() {
        return isDel;
    }

    public GuideFocusVO setIsDel(int isDel) {
        this.isDel = isDel;
        return this;
    }

    public int getWeights() {
        return weights;
    }

    public GuideFocusVO setWeights(int weights) {
        this.weights = weights;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public GuideFocusVO setAudio(String audio) {
        this.audio = audio;
        return this;
    }
}
