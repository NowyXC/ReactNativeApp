import React, {Component} from 'react';
import { StyleSheet, Text,TouchableOpacity,DeviceEventEmitter, View} from 'react-native';

import {
    createStackNavigator,
  } from 'react-navigation';
  
import Test from './Test';
import CallNativeToast from './CallNativeToast';
import ImagePage from './ImagePage'

import CallNativeAndCallback from './CallNativeAndCallback';


class Main extends React.Component{
    
    constructor(props){
        super(props);
        this.state ={
            data:{
                height:0,
                width:0
            }
        }
    }
    
    componentWillMount() {
        DeviceEventEmitter.addListener('windowInfoEvent', (data) => {
            const {windowInfoHeight,windowInfoWidth} = data;
            if(windowInfoHeight != null){
                this.setState({
                    data:{
                        height:windowInfoHeight,
                        width :windowInfoWidth
                    }
                });
            }
        });
      }

    render(){
        const { navigate } =  this.props.navigation;
        return(
            <View style={styles.container}>
                <Text style={styles.title}>ReactNative页面</Text>
                <Text style={styles.version}>热更新测试</Text>
                <View style={styles.content}>
                    <TouchableOpacity

                        onPress={
                            ()=>{
                                navigate('CallNativeToast');
                            }
                        }
                    >
                        <Text style={styles.button}>调用原生方法</Text>
                    </TouchableOpacity>

                    <TouchableOpacity
                        onPress={
                            ()=>{
                                navigate('CallNativeAndCallback');
                            }
                        }
                        >
                        <Text style={styles.button}>调用原生方法,并回调</Text>
                    </TouchableOpacity>
                   
                    <TouchableOpacity
                        onPress={
                            ()=>{
                                navigate('ImagePage');
                            }
                        }
                        >
                        <Text style={styles.button}>热更新带图片资源</Text>
                    </TouchableOpacity>


                </View>
                <View style={styles.windowInfo}>
                    <Text style={styles.bottomTitle}>native向react-native传递信息</Text>
                    <Text style={styles.bottomTint}>（native调用react-native方法）</Text>
                    <Text style={styles.bottomTitle}>窗体的信息:</Text>
                    <Text style={styles.bottomMsg}>
                        高度：{this.state.data.height?this.state.data.height:0} --宽度：{this.state.data.width?this.state.data.width:0}</Text>
                </View>
                
            </View>
        );
    }
  
}



const App = createStackNavigator({
    Main:{screen:Main},
    Test: { screen: Test },
    ImagePage:{screen:ImagePage},
    CallNativeToast: { screen: CallNativeToast },
    CallNativeAndCallback:{screen:CallNativeAndCallback}
  },{
      initialRouteName:"Main",
      headerMode:'none'
  });
  
export default App;


const styles = StyleSheet.create({
    container: {
      flex: 1,
      flexDirection:'column',
      alignItems: 'center',
      backgroundColor: '#F5FCFF',
    },
    title:{
        fontSize:18,
        color:'#111'
    },
    version:{
        fontSize:16,
        color:'#333',
        marginTop: 10
    },
    content: {
        flex: 1,
        flexDirection:'column',
        alignItems: 'center',
        justifyContent:'center'
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
    },
    windowInfo:{
        flexDirection:'column',
        
    },
    bottomTitle:{
        fontSize:16,
        color:'#111'
    },
    bottomTint:{
        fontSize:12,
        color:'#999'
    },
    bottomMsg:{
        fontSize:14,
        color:'#333',
    }
    
    
  });