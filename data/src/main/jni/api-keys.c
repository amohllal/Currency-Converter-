#include <jni.h>

//For first API key
JNIEXPORT jstring

JNICALL
Java_com_ahmed_data_core_Keys_getAPIKey(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "af7ddd7b15d09bfff5a5");
}
JNICALL
Java_com_ahmed_data_core_Keys_getBaseUrl(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "https://free.currconv.com/api/v7/");
}