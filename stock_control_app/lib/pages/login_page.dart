import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

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

    try {
      final response = await http.post(
        Uri.parse('http://192.168.x.x:8080/User/login'), // Use IP address if testing on a device
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          'email': _emailController.text,
          'password': _passwordController.text,
        }),
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        final token = responseData['token'];
        final email = responseData['email'];
        final authorities = List<String>.from(responseData['authorities']);

        await _storage.write(key: 'token', value: token);
        await _storage.write(key: 'email', value: email);
        await _storage.write(key: 'authorities', value: jsonEncode(authorities)); // Store as JSON

        _navigateBasedOnAuthorities(authorities);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Login failed. Status code: ${response.statusCode}')),
        );
      }
    } catch (e, stacktrace) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('An error occurred: $e')),
      );
      print('Error: $e');
      print('Stacktrace: $stacktrace');
    }
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
