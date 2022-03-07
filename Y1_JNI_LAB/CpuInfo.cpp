#include <ntstatus.h>
#include <windows.h>
#include <iostream>
#include"Main.h"
#include <winternl.h>
#include <string>
#include <sstream>

JNIEXPORT jstring JNICALL Java_Main_getCpuInfo
  (JNIEnv *env, jobject obj) {
        std::ostringstream ss;
        SYSTEM_INFO siSysInfo;
        GetSystemInfo(&siSysInfo);
        char msg[1000] = "Hardware information: \n  Number of processors: ";
        jstring result;
        ss << siSysInfo.dwNumberOfProcessors;
        strcat(msg, ss.str().c_str());
        if (siSysInfo.dwProcessorType == 8664){
            strcat(msg, "  Processor type: AMD");
        } else {
            strcat(msg, "  Processor type: ");
            ss << siSysInfo.dwProcessorType;
            strcat(msg, ss.str().c_str());
        }
        puts(msg);
        result = env->NewStringUTF(msg);
        return result;
  }
