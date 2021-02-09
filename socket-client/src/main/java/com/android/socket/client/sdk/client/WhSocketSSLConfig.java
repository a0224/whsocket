package com.android.socket.client.sdk.client;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class WhSocketSSLConfig {

    private String mProtocol;

    private TrustManager[] mTrustManagers;

    private KeyManager[] mKeyManagers;

    private SSLSocketFactory mCustomSSLFactory;

    private WhSocketSSLConfig() {

    }

    public static class Builder {
        private WhSocketSSLConfig mConfig;

        public Builder() {
            mConfig = new WhSocketSSLConfig();
        }

        public Builder setProtocol(String protocol) {
            mConfig.mProtocol = protocol;
            return this;
        }

        public Builder setTrustManagers(TrustManager[] trustManagers) {
            mConfig.mTrustManagers = trustManagers;
            return this;
        }

        public Builder setKeyManagers(KeyManager[] keyManagers) {
            mConfig.mKeyManagers = keyManagers;
            return this;
        }

        public Builder setCustomSSLFactory(SSLSocketFactory customSSLFactory) {
            mConfig.mCustomSSLFactory = customSSLFactory;
            return this;
        }

        public WhSocketSSLConfig build() {
            return mConfig;
        }
    }

    public KeyManager[] getKeyManagers() {
        return mKeyManagers;
    }

    public String getProtocol() {
        return mProtocol;
    }

    public TrustManager[] getTrustManagers() {
        return mTrustManagers;
    }

    public SSLSocketFactory getCustomSSLFactory() {
        return mCustomSSLFactory;
    }
}
