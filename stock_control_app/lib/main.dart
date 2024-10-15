import 'package:flutter/material.dart';
import 'package:stock_control_app/pages/login_page.dart';
import 'Pages/Admin_page.dart';
import 'Pages/projects_page.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Stock Controle App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home:ProjectsPage(),
      routes: {
        '/login': (context) => Login(),
        '/projects':(context)=>ProjectsPage(),
        '/adminPage': (context) => AdminPage(),
      },
    );
  }
}
