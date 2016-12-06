#include "encrypt.h"
#include "com_android_common_utils_EncryptUtil.h"
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>


pthread_t t_id;

int getnumberfor_str(char *str) {
    if (str == NULL) {
        return -1;
    }
    char result[20];
    int count = 0;
    while (*str != '\0') {
        if (*str >= 48 && *str <= 57) {
            result[count] = *str;
            count++;
        }
        str++;
    }
    int val = atoi(result);
    return val;
}

void thread_fuction() {
    int pid = getpid();
    char file_name[20] = {'\0'};
    sprintf(file_name, "/proc/%d/status", pid);
    char linestr[256];
    int i = 0, traceid;
    FILE *fp;
    while (1) {
        i = 0;
        fp = fopen(file_name, "r");
        if (fp == NULL) {
            break;
        }
        while (!feof(fp)) {
            fgets(linestr, 256, fp);
            if (i == 5) {
                traceid = getnumberfor_str(linestr);
                if (traceid > 0) {
                    LOGD("I was be traced...trace pid:%d", traceid);
                    exit(0);
                }
                break;
            }
            i++;
        }
        fclose(fp);
        sleep(5);
    }

}

void create_thread_check_traceid() {
    int err = pthread_create(&t_id, NULL, thread_fuction, NULL);
    if (err != 0) {
        LOGD("create thread fail: %s\n", strerror(err));
    }
}

//定义目标类名称
static const char *className = "com/android/common/utils/EncryptUtil";

//定义方法隐射关系
static JNINativeMethod methods[] = {
};

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGD("JNI on load...");

    //检测自己有没有被trace
    create_thread_check_traceid();

    //声明变量
    jint result = JNI_ERR;
    JNIEnv *env = NULL;
    jclass clazz;
    int methodsLenght;

    //获取JNI环境对象
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGD("ERROR: GetEnv failed\n");
        return JNI_ERR;
    }

    LOGD("JNIEnv:%p", env);
    LOGD("start equal signature...");
//    int check_sign = equal_sign(env);
//    LOGD("check_sign:%d", check_sign);
//    if(check_sign == 0){
//        exit(0);
//    }

    //注册本地方法.Load 目标类
    clazz = (*env)->FindClass(env, className);
    if (clazz == NULL) {
        LOGD("Native registration unable to find class '%s'", className);
        return JNI_ERR;
    }

    //建立方法隐射关系
    //取得方法长度
    methodsLenght = sizeof(methods) / sizeof(methods[0]);
    if ((*env)->RegisterNatives(env, clazz, methods, methodsLenght) < 0) {
        LOGD("RegisterNatives failed for '%s'", className);
        return JNI_ERR;
    }

    result = JNI_VERSION_1_4;
    return result;
}

//onUnLoad方法，在JNI组件被释放时调用
void JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOGD("JNI unload...");
}
