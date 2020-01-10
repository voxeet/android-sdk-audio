package com.voxeet.audio2.devices;

import android.media.AudioManager;
import android.support.annotation.NonNull;

import com.voxeet.audio.focus.AudioFocusManager;
import com.voxeet.audio.focus.AudioFocusMode;
import com.voxeet.audio.mode.MediaMode;
import com.voxeet.audio2.devices.description.ConnectionState;
import com.voxeet.audio2.devices.description.DeviceType;
import com.voxeet.audio2.devices.description.IMediaDeviceConnectionState;
import com.voxeet.promise.Promise;

public class NormalMediaDevice extends MediaDevice<DeviceType> {

    @NonNull
    private AudioManager audioManager;
    private AudioFocusManager focusManager = new AudioFocusManager(AudioFocusMode.MEDIA);

    @NonNull
    private MediaMode mode;

    public NormalMediaDevice(
            @NonNull AudioManager audioManager,
            @NonNull IMediaDeviceConnectionState connectionState,
            @NonNull DeviceType deviceType,
            @NonNull String id) {
        super(connectionState, deviceType, id);

        this.audioManager = audioManager;
        mode = new MediaMode(audioManager, focusManager);
    }

    @Override
    protected Promise<Boolean> connect() {
        return new Promise<>(solver -> {
            setConnectionState(ConnectionState.CONNECTING);
            mode.apply(false);
            setConnectionState(ConnectionState.CONNECTED);
            solver.resolve(true);
        });
    }

    @Override
    protected Promise<Boolean> disconnect() {
        return new Promise<>(solver -> {
            setConnectionState(ConnectionState.DISCONNECTING);
            mode.apply(false);
            setConnectionState(ConnectionState.DISCONNECTED);
            solver.resolve(true);
        });
    }
}