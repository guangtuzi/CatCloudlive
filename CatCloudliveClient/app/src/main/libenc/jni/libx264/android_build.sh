#!/bin/sh

ANDROID_NDK=/home/pan/android-ndk-r10b
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-arm
CROSS_PREFIX=$ANDROID_NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86/bin/arm-linux-androideabi-
EXTRA_CFLAGS="-march=armv7-a -mfloat-abi=softfp -mfpu=neon -D__ANDROID__ -D__ARM_ARCH_7__ -D__ARM_ARCH_7A__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/armeabi-v7a
./configure --prefix=$PREFIX \
        --host=arm-linux \
        --sysroot=$SYSROOT \
        --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
        --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash

make clean
make STRIP= -j8 install || exit 1
cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig

### armeabi ###
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-arm
CROSS_PREFIX=$ANDROID_NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86/bin/arm-linux-androideabi-
EXTRA_CFLAGS="-march=armv5te -msoft-float -D__ANDROID__ -D__ARM_ARCH_5TE__ -D__ARM_ARCH_5TEJ__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/armeabi

./configure --prefix=$PREFIX \
        --host=arm-linux-androideabi \
        --sysroot=$SYSROOT \
        --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
        --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash \
        --disable-asm

make clean
make STRIP= -j8 install || exit 1
cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig

### x86 ###
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-x86
CROSS_PREFIX=$ANDROID_NDK/toolchains/x86-4.9/prebuilt/linux-x86/bin/i686-linux-android-
EXTRA_CFLAGS="-march=i686 -mtune=i686 -m32 -mmmx -msse2 -msse3 -mssse3 -D__ANDROID__ -D__i686__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/x86

./configure --prefix=$PREFIX \
        --host=i686-linux-android \
        --sysroot=$SYSROOT \
        --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
        --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash

make clean
make STRIP= -j8 install || exit 1
cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig

### mips ###
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-mips
CROSS_PREFIX=$ANDROID_NDK/toolchains/mipsel-linux-android-4.9/prebuilt/linux-x86/bin/mipsel-linux-android-
EXTRA_CFLAGS="-march=mips32 -mfp32 -mhard-float -D__ANDROID__ -D__mips__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/mips

./configure --prefix=$PREFIX \
        --host=mipsel-linux-android \
        --sysroot=$SYSROOT \
        --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
        --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash \
        --disable-asm

make clean
make STRIP= -j8 install || exit 1
cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig

### arm64-v8a ###
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-arm64
CROSS_PREFIX=$ANDROID_NDK/toolchains/aarch64-linux-android-4.9/prebuilt/linux-x86/bin/aarch64-linux-android-
EXTRA_CFLAGS="-march=armv8-a -D__ANDROID__ -D__ARM_ARCH_8__ -D__ARM_ARCH_8A__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/arm64-v8a

./configure --prefix=$PREFIX \
        --host=aarch64-linux-android \
        --sysroot=$SYSROOT \
        --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
       --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash

make clean
make STRIP= -j8 install || exit 1
cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig



### x86_64 ###
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-x86_64
CROSS_PREFIX=$ANDROID_NDK/toolchains/x86_64-4.9/prebuilt/linux-x86/bin/x86_64-linux-android-
EXTRA_CFLAGS="-march=core-avx-i -mtune=core-avx-i -m64 -mmmx -msse2 -msse3 -mssse3 -msse4.1 -msse4.2 -mpopcnt -D__ANDROID__ -D__x86_64__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/x86_64

./configure --prefix=$PREFIX \
        --host=x86_64-linux-android \
        --sysroot=$SYSROOT \
        --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
        --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash

make clean
make STRIP= -j8 install || exit 1
cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig



### mips64 ###
SYSROOT=$ANDROID_NDK/platforms/android-L/arch-mips64
CROSS_PREFIX=$ANDROID_NDK/toolchains/mips64el-linux-android-4.9/prebuilt/linux-x86/bin/mips64el-linux-android-
EXTRA_CFLAGS="-march=mips64r6 -D__ANDROID__ -D__mips__"
EXTRA_LDFLAGS="-nostdlib"
PREFIX=`pwd`/../lib/mips64

./configure --prefix=$PREFIX \
        --host=mips64el-linux-android \
       --sysroot=$SYSROOT \
       --cross-prefix=$CROSS_PREFIX \
        --extra-cflags="$EXTRA_CFLAGS" \
        --extra-ldflags="$EXTRA_LDFLAGS" \
        --enable-pic \
        --enable-static \
        --enable-strip \
        --disable-cli \
        --disable-win32thread \
        --disable-avs \
        --disable-swscale \
        --disable-lavf \
        --disable-ffms \
        --disable-gpac \
        --disable-lsmash \
        --disable-asm

make clean
make STRIP= -j8 install || exit 1



cp -f $PREFIX/lib/libx264.a $PREFIX
rm -rf $PREFIX/include $PREFIX/lib $PREFIX/pkgconfig
