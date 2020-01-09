package com.voxeet.audio2.manager;

import android.support.annotation.NonNull;

import com.voxeet.audio2.devices.NormalDevice;
import com.voxeet.audio2.devices.NormalMediaDevice;
import com.voxeet.audio2.devices.SpeakerDevice;
import com.voxeet.audio2.devices.description.DeviceType;
import com.voxeet.audio2.devices.description.IMediaDeviceConnectionState;
import com.voxeet.audio2.devices.MediaDevice;
import com.voxeet.audio2.system.SystemAudioManager;
import com.voxeet.promise.Promise;

import java.util.ArrayList;
import java.util.List;

public class SystemDeviceManager extends AbstractDeviceManager<MediaDevice> {

    private final SpeakerDevice speakerDevice;
    private final NormalDevice normalDevice;
    private final NormalMediaDevice normalMediaDevice;

    private SystemAudioManager systemAudioManager;

    private ArrayList<MediaDevice> list;

    public SystemDeviceManager(@NonNull SystemAudioManager systemAudioManager,
                               @NonNull IMediaDeviceConnectionState connectionState) {
        super(connectionState);

        this.systemAudioManager = systemAudioManager;

        this.speakerDevice = new SpeakerDevice(systemAudioManager.audioManager(),
                connectionState,
                DeviceType.EXTERNAL_SPEAKER,
                "external_speaker");
        this.normalDevice = new NormalDevice(systemAudioManager.audioManager(),
                connectionState,
                DeviceType.INTERNAL_SPEAKER,
                "internal_speaker");
        this.normalMediaDevice = new NormalMediaDevice(systemAudioManager.audioManager(),
                connectionState,
                DeviceType.NORMAL_MEDIA,
                "normal_media");

        this.list = new ArrayList<>();
        list.add(normalDevice);
        list.add(speakerDevice);
        list.add(normalMediaDevice);
    }

    @NonNull
    @Override
    public Promise<List<MediaDevice>> enumerateDevices() {
        return Promise.resolve(list);
    }
}
