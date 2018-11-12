import React, {Component} from 'react';
import {
    StyleSheet, 
    Text,
    TouchableOpacity,
    View} from 'react-native';

import RefreshModule from './native/RefreshModule';


export default class CallNativeAndCallback extends React.Component{
    constructor(props){
        super(props);
        
    }


    render(){
        return(
         
            <View style={styles.container}> 
            <Text style={styles.tips}>随机生成成功或者失败对话框：</Text>
                <TouchableOpacity
                    onPress={() => {
                        let data = "react-native原数据";
                        RefreshModule.refresh(data,
                        (refreshMsg,responseTime,successResponseText)=>{
                            alert(
                                "原数据:"+data +
                                "\n原数据处理："+refreshMsg
                                +"\n结果："+successResponseText);
                        },
                        errorMsg =>{
                            alert(errorMsg);
                        }
                        );
                    }}
                    >
                    <Text style={styles.button}>点击调用原生方法，并回调结果!</Text>
                </TouchableOpacity>

               
            </View>
        );
    }

}
const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#F5FCFF',
    },
    tips:{
        color:"#666",
        fontSize:14
    },
    button:{
        color:'white',
        height:40,
        textAlign:'center',
        textAlignVertical:'center',
        backgroundColor:'#303f9f',
        fontSize:14,
        paddingHorizontal:16,
        marginBottom:10
    }
   
    
  });