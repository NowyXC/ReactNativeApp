import React, {Component} from 'react';
import {
    StyleSheet, 
    Text,
    Image,
    View} from 'react-native';





export default class ImagePage extends React.Component{
    constructor(props){
        super(props);
    }
    render(){
        return(

            <View style={styles.container}> 

             <Text>显示项目默认目录下的图片</Text>
                <Image source={
                    require('../images/ic_user_avatar_def.png')}
                    style={styles.img}
                />
                

             <Text>显示bundle文件路径的图片</Text>
                <Image source={
                    require('../images/ic_evaluation_satisfied_green.png')}
                    style={styles.img}
                />
               
              
            </View>
        );
    }

}
const styles = StyleSheet.create({
    container: {
      flex: 1,
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#F5FCFF',
    },
    img: {
        width:200,
        height:200
    }
    
  });