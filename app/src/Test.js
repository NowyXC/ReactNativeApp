import React, {Component} from 'react';
import {Platform, StyleSheet, Text,Image, View} from 'react-native';

export default class Test extends React.Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            // 显示一张图片
            <View style={styles.container}> 
                <Image source={
                    require('../images/ic_user_avatar_def.png')}
                    style={styles.img}
                />
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
    img: {
        width:200,
        height:200
    }
    
  });