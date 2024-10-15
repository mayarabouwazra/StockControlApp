import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:http/http.dart';

class Login extends StatefulWidget {
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _storage = FlutterSecureStorage();

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _login() async {
    if (_emailController.text.isEmpty || _passwordController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please enter both email and password.')),
      );
      return;
    }

    int retryCount = 0;
    const int maxRetries = 3;

    while (retryCount < maxRetries) {
      try {
        print('Attempting login to http://172.16.20.104/User/login');
        final response = await http.post(
          Uri.parse('http://172.16.20.104:8080/User/login'),
          headers: {"Content-Type": "application/json"},
          body: jsonEncode({
            'email': _emailController.text,
            'password': _passwordController.text,
          }),
        );

        print('Response: ${response.statusCode}, Body: ${response.body}'); // Debugging line

        if (response.statusCode == 200) {
          final responseData = json.decode(response.body);
          final token = responseData['token'];
          final email = responseData['email'];
          final authorities = List<String>.from(responseData['authorities']);

          await _storage.write(key: 'token', value: token);
          await _storage.write(key: 'email', value: email);
          await _storage.write(key: 'authorities', value: jsonEncode(authorities)); // Store as JSON

          _navigateBasedOnAuthorities(authorities);

          return;
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Login failed. Status code: ${response.statusCode}')),
          );
          print('Login failed with status code: ${response.statusCode}');
          print('Response body: ${response.body}');
        }
      } on SocketException catch (e) {
        print('SocketException: $e'); // Debugging line
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Socket exception: $e')),
        );
      } on ClientException catch (e) {
        print('ClientException: $e'); // Debugging line
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Client exception: $e')),
        );
      } catch (e, stacktrace) {
        print('Error: $e, Stacktrace: $stacktrace'); // Debugging line
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e')),
        );
        print('Error: $e');
        print('Stacktrace: $stacktrace');
      }

      retryCount++;
      await Future.delayed(Duration(seconds: 1));
    }

    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Failed to connect to server. Please try again.')),
    );
  }
  void _navigateBasedOnAuthorities(List<String> authorities) {
    if (authorities.contains('Admin')) {
      Navigator.pushReplacementNamed(context, '/adminPage');
    } else if (authorities.contains('Engineer')) {
      Navigator.pushReplacementNamed(context, '/projects');
    } else if (authorities.contains('StockAdmin')) {
      Navigator.pushReplacementNamed(context, '/stockAdminPage');
    } else {
      Navigator.pushReplacementNamed(context, '/userPage');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(
              controller: _emailController,
              decoration: InputDecoration(
                hintText: "Email",
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(18),
                  borderSide: BorderSide.none,
                ),
                fillColor: Colors.purple.withOpacity(0.1),
                filled: true,
                prefixIcon: const Icon(Icons.person),
              ),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _passwordController,
              decoration: InputDecoration(
                hintText: "Password",
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(18),
                  borderSide: BorderSide.none,
                ),
                fillColor: Colors.purple.withOpacity(0.1),
                filled: true,
                prefixIcon: const Icon(Icons.password),
              ),
              obscureText: true,
            ),
            const SizedBox(height: 10),
            ElevatedButton(
              onPressed:  _login,
              child: Text('Login'),
            ),
          ],
        ),
      ),
    );
  }
}
