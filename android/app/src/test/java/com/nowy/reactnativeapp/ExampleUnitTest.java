package com.nowy.reactnativeapp;

import com.nowy.reactnativeapp.utils.diff_match_patch;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDiffMatch(){
        String strA = "a123asd4562323fewa123asd4562323fewa123asd4562323fewa123asd4562323fewa123asd4562323fewa123asd4562323few";
        String strB = "b123add4fwe86a123asd4562323fewa123asd4562323fewb123add4fwe86a123asd4562323fewa123asd4562323fewb123add4fwe86a123asd4562323fewa123asd4562323few";

        diff_match_patch dmp  = new diff_match_patch();

        //获取差异化列表
//        LinkedList<diff_match_patch.Diff> diffs = dmp.diff_main(strB,strA);
        //生成diff_main(text1,text2)中的(text1)的补丁列表
//        LinkedList<diff_match_patch.Patch> patches = dmp.patch_make(diffs);

        LinkedList<diff_match_patch.Patch> patches = dmp.patch_make(strB,strA);
        // 生成补丁包字符串
        String patchesStr = dmp.patch_toText(patches);

        //-----------------------------------------------------

        diff_match_patch dmp2  = new diff_match_patch();
        // 4.转换pat
        LinkedList<diff_match_patch.Patch> pathes = (LinkedList<diff_match_patch.Patch>) dmp2.patch_fromText(patchesStr);
        // 5.与assets目录下的bundle合并，生成新的bundle
        Object[] strBArray = dmp2.patch_apply(pathes, strB);
        System.out.println(strBArray[0]);
        assertEquals(strBArray[0],strA);
        boolean[] matchArr = (boolean[]) strBArray[1];
        for(int i=0;i< matchArr.length;i++){
            System.out.println("i:"+i+" -> "+matchArr[i]);
        }
    }


    private void mergePatAndAsset() {

        // 1.获取Assets目录下的bunlde
//        String assetsBundle = RefreshUpdateUtils.getJsBundleFromAssets(getApplicationContext());
        // 2.获取.pat文件字符串
//        String patcheStr = RefreshUpdateUtils.getStringFromPat(FileConstant.JS_PATCH_LOCAL_FILE);

        // 3.初始化 dmp
        diff_match_patch dmp = new diff_match_patch();
//        // 4.转换pat
//        LinkedList<diff_match_patch.Patch> pathes = (LinkedList<diff_match_patch.Patch>) dmp.patch_fromText(patcheStr);
//        // 5.与assets目录下的bundle合并，生成新的bundle
//        Object[] bundleArray = dmp.patch_apply(pathes, assetsBundle);
//        // 6.保存新的bundle
//        try {
//            Writer writer = new FileWriter(FileConstant.JS_BUNDLE_LOCAL_PATH);
//            String newBundle = (String) bundleArray[0];
//            writer.write(newBundle);
//            writer.close();
//            // 7.删除.pat文件
//            File patFile = new File(FileConstant.JS_PATCH_LOCAL_FILE);
//            patFile.delete();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public boolean isCompleted(Object[] results){
        Boolean[] resultStatusArr = (Boolean[])results[1];
        boolean isSuccess = true;
        for(boolean s : resultStatusArr){
            isSuccess =  isSuccess && s;
            if(!isSuccess) return false;
        }

        return isSuccess;
    }

}