#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_my_hiltapplication_Value_a(
        JNIEnv *env,
        jobject /* this */) {
    std::string refresh_token_url = "test test";
    return env->NewStringUTF(refresh_token_url.c_str());
}