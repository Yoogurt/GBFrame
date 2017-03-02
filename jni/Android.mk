LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := gbframe
LOCAL_SRC_FILES := JniBridge.cpp
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)
