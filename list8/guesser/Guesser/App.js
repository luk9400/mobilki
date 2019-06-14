/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, TextInput, Button } from 'react-native';

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max - 1) + 1);
}

type Props = {};
type State = { input: String, answer: String }
export default class App extends Component<Props> {

  constructor(props) {
    super(props);
    this.state = {
      input: '',
      answer: ''
    }
    this.number = getRandomInt(100);
  }

  generateNewNumber() {
    this.number = getRandomInt(100);
    this.setState({answer: ''});
    console.log('Number: ' + this.number);
  }

  click() {
    let guess = parseInt(this.state.input);
    console.log('Guess: ' + guess)
    if (this.number > guess) {
      this.setState({ answer: 'More' });
    } else if (this.number < guess) {
      this.setState({ answer: 'Less' });
    } else if (this.number === guess) {
      this.setState({ answer: "You've guessed!" });
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Guesser</Text>
        <Text style={styles.instructions}>Try to guesss the number from 1 to 100</Text>
        <Text style={styles.instructions}>{this.state.answer}</Text>
        <TextInput style={styles.input} onChangeText={(input) => this.setState({ input })}
          value={this.input} keyboardType='number-pad'></TextInput>
        <Button style={styles.btn} title='Guess' onPress={this.click.bind(this)}></Button>
        <Button style={styles.btn} title='Generate new number' onPress={this.generateNewNumber.bind(this)}></Button>
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
  btn: {
    margin: 10,
    width: 300,
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  input: {
    height: 40,
    width: 100,
    borderColor: 'gray',
    borderWidth: 1,
    margin: 10,
  },
});
