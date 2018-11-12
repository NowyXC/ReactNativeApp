import React, {Component} from 'react';
import {
    StyleSheet, 
    Text,
    TouchableOpacity,
    View} from 'react-native';

import {NativeModules} from 'react-native';
import ToastSample from './native/ToastSample';

export default class CallNativeToast extends React.Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            <View style={styles.container}> 
                <TouchableOpacity
                    onPress={() => {
                        // NativeModules.ToastSample.show("toast提示",NativeModules.ToastSample.SHORT);
                        ToastSample.show("toast提示",ToastSample.SHORT);
                    }}
                    >
                    <Text style={styles.button}>点击弹出toast!</Text>
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
    button:{
        color:'white',
        height:40,
        textAlign:'center',
        textAlignVertical:'center',
        backgroundColor:'#303f9f',
        fontSize:14,
        paddingHorizontal:16
    }
    
  });