import React, {Component} from 'react';

/**
 * react-native使用的资源文件在编译后会根据存放的路径重命名。
 * 例如：资源文件在/app/images/下的image.png，会被命名为：app_images_image.png.
 * android默认存放于：/android/app/src/main/res/drawable-mdpi/目录下
 */
//资源文件在/app/images/下，使用此前缀拼接
const ImagesPrefix = "app_images_"

//原来的资源文件，存在于APP资源目录中
const originalImgs = [
    ImagesPrefix+'ic_user_avatar_def.png' /*头像图*/
]

//增量更新的资源文件，存在于外部bundle文件所在目录中
const externalImgs_V1 = [
    ImagesPrefix+'ic_evaluation_satisfied_green.png' /*笑脸图*/
]



export {
    originalImgs,
    externalImgs_V1 as externalImgs
}