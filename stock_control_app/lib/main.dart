import 'package:flutter/material.dart';
import 'package:stock_control_app/pages/login_page.dart';
import 'Pages/Admin_page.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'User Page',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Login(),
      routes: {
        '/login': (context) => Login(),

        '/adminPage': (context) => AdminPage(),
      },
    );
  }
}
