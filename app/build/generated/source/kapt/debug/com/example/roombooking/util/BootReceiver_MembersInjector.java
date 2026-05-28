package com.example.roombooking.util;

import com.example.roombooking.data.repository.SyncPreferences;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<SyncPreferences> syncPrefsProvider;

  public BootReceiver_MembersInjector(Provider<SyncPreferences> syncPrefsProvider) {
    this.syncPrefsProvider = syncPrefsProvider;
  }

  public static MembersInjector<BootReceiver> create(Provider<SyncPreferences> syncPrefsProvider) {
    return new BootReceiver_MembersInjector(syncPrefsProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectSyncPrefs(instance, syncPrefsProvider.get());
  }

  @InjectedFieldSignature("com.example.roombooking.util.BootReceiver.syncPrefs")
  public static void injectSyncPrefs(BootReceiver instance, SyncPreferences syncPrefs) {
    instance.syncPrefs = syncPrefs;
  }
}
