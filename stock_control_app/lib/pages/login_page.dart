import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Login extends StatelessWidget {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  void login(String email, String password, BuildContext context) async {
    final response = await http.post(
      Uri.parse('http://localhost:8080/User/login'),
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({
        'email': email,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      final responseData = json.decode(response.body);
      final String token = responseData['token'];
      final String email = responseData['email'];
      final List<String> authorities = List<String>.from(responseData['authorities']);

      // Store token, email, and authorities in SharedPreferences
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setString('token', token);
      await prefs.setString('email', email);
      await prefs.setStringList('authorities', authorities);

      // Navigate based on authorities
      navigateBasedOnAuthorities(context, authorities);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Login failed. Please check your credentials.')),
      );
    }
  }

  void navigateBasedOnAuthorities(BuildContext context, List<String> authorities) {
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
              onPressed: () {
                login(_emailController.text, _passwordController.text, context);
              },
              child: Text('Login'),
            ),
          ],
        ),
      ),
    );
  }
}
