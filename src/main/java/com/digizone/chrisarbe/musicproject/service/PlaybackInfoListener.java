package com.digizone.chrisarbe.musicproject.service;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.digizone.chrisarbe.musicproject.service.players.MediaPlayerAdapter;

public abstract class PlaybackInfoListener {

    public abstract void onPlaybackStateChange(PlaybackStateCompat state);

    public void onPlaybackCompleted() {
    }
}