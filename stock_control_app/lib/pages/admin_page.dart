import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class AdminPage extends StatefulWidget {
  @override
  _AdminPageState createState() => _AdminPageState();
}

class _AdminPageState extends State<AdminPage> {
  bool _isProjectFormVisible = false;
  bool _isEngineerFormVisible = false;
  bool _isStockAdminFormVisible = false;
  bool _isLoading = false;

  final TextEditingController _projectNameController = TextEditingController();
  final TextEditingController _projectDescriptionController = TextEditingController();
  final TextEditingController _projectLocationController = TextEditingController();
  final TextEditingController _engineerMailController = TextEditingController();

  final TextEditingController _engineerNameController = TextEditingController();
  final TextEditingController _engineerLastNameController = TextEditingController();


  final TextEditingController _engineerEmailController = TextEditingController();

  final TextEditingController _stockAdminEmailController = TextEditingController();
  final TextEditingController _stockAdminNameController = TextEditingController();
  final TextEditingController _stockAdminLastNameController = TextEditingController();



  Future<void> _addProject() async {
    setState(() {
      _isLoading = true;
    });

    final String projectName = _projectNameController.text;

    final String projectDescription = _projectDescriptionController.text;
    final String projectLocation = _projectLocationController.text;
    final String engineerMail = _engineerMailController.text;

    if (projectName.isEmpty || projectDescription.isEmpty || projectLocation.isEmpty || engineerMail.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please fill out all fields.')),
      );
      setState(() {
        _isLoading = false;
      });
      return;
    }

    final String apiUrl = 'http://localhost:8080/admin/projects';

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          "projectName": projectName,
          "projectDescription": projectDescription,
          "projectLocation": projectLocation,
          "engineerMail": engineerMail,
        }),
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Project added successfully!')),
        );
        _projectNameController.clear();
        _projectDescriptionController.clear();
        _projectLocationController.clear();
        _engineerMailController.clear();
        setState(() {
          _isProjectFormVisible = false;
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to add project. Please try again.')),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('An error occurred. Please try again.')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  Future<void> _createEngineerProfile() async {
    setState(() {
      _isLoading = true;
    });
    final String engineerLastName = _engineerLastNameController.text;
    final String engineerName = _engineerNameController.text;
    final String engineerEmail = _engineerEmailController.text;

    if (engineerName.isEmpty || engineerEmail.isEmpty ||engineerLastName.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please fill out all fields.')),
      );
      setState(() {
        _isLoading = false;
      });
      return;
    }

    final String apiUrl = 'http://localhost:8080/admin/createEngineer';

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          "nom": engineerName,
          "prenom": engineerLastName,
          "email": engineerEmail,
        }),
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Engineer profile created successfully!')),
        );
        _engineerNameController.clear();
        _engineerLastNameController.clear();
        _engineerEmailController.clear();
        setState(() {
          _isEngineerFormVisible = false;
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to create engineer profile. Please try again.')),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('An error occurred. Please try again.')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  Future<void> _createStockAdminProfile() async {
    setState(() {
      _isLoading = true;
    });

    final String stockAdminEmail = _stockAdminEmailController.text;
    final String StockAdminName = _stockAdminNameController.text;
    final String StockAdminLastName = _stockAdminLastNameController.text;

    if (stockAdminEmail.isEmpty || StockAdminName.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please fill out all fields.')),
      );
      setState(() {
        _isLoading = false;
      });
      return;
    }

    final String apiUrl = 'http://localhost:8080/stockadmin/create';

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          "email": stockAdminEmail,
          "name": StockAdminName,
          "last name":StockAdminLastName,
        }),
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Stock Admin profile created successfully!')),
        );
        _stockAdminEmailController.clear();
        _stockAdminNameController.clear();
        _stockAdminLastNameController.clear();
        setState(() {
          _isStockAdminFormVisible = false;
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to create stock admin profile. Please try again.')),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('An error occurred. Please try again.')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  void _showButtons() {
    setState(() {
      _isProjectFormVisible = false;
      _isEngineerFormVisible = false;
      _isStockAdminFormVisible = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Admin Page')),
      body: Container(
        child: Stack(
          children: [
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  if (_isProjectFormVisible) ...[
                    TextField(
                      controller: _projectNameController,
                      decoration: InputDecoration(labelText: 'Project Name', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _projectDescriptionController,
                      decoration: InputDecoration(labelText: 'Project Description', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _projectLocationController,
                      decoration: InputDecoration(labelText: 'Project Location', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _engineerMailController,
                      decoration: InputDecoration(labelText: 'Engineer Email', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: _isLoading ? null : _addProject,
                      child: _isLoading ? CircularProgressIndicator() : Text('Add Project'),
                    ),
                  ],
                  if (_isEngineerFormVisible) ...[
                    TextField(
                      controller: _engineerNameController,
                      decoration: InputDecoration(labelText: 'Engineer Name', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _engineerLastNameController,
                      decoration: InputDecoration(labelText: 'Engineer last  Name', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _engineerEmailController,
                      decoration: InputDecoration(labelText: 'Engineer Email', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: _isLoading ? null : _createEngineerProfile,
                      child: _isLoading ? CircularProgressIndicator() : Text('Create Engineer Profile'),
                    ),
                  ],
                  if (_isStockAdminFormVisible) ...[
                    TextField(
                      controller: _stockAdminEmailController,
                      decoration: InputDecoration(labelText: 'Stock Admin Email', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _stockAdminNameController,
                      decoration: InputDecoration(labelText: 'Stock Admin name', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 5),
                    TextField(
                      controller: _stockAdminLastNameController,
                      decoration: InputDecoration(labelText: 'Stock Admin  last name', border: OutlineInputBorder()),
                    ),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: _isLoading ? null : _createStockAdminProfile,
                      child: _isLoading ? CircularProgressIndicator() : Text('Create Stock Admin Profile'),
                    ),
                  ],
                ],
              ),
            ),
            if (!_isProjectFormVisible && !_isEngineerFormVisible && !_isStockAdminFormVisible)
              Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        FloatingActionButton(
                          onPressed: () {
                            setState(() {
                              _isProjectFormVisible = true;
                              _isEngineerFormVisible = false;
                              _isStockAdminFormVisible = false;
                            });
                          },
                          child: Icon(Icons.add),
                          heroTag: null,
                          backgroundColor: Colors.lightBlue,
                          tooltip: 'Add Project',
                        ),
                        SizedBox(width: 30),
                        FloatingActionButton(
                          onPressed: () {
                            setState(() {
                              _isEngineerFormVisible = true;
                              _isProjectFormVisible = false;
                              _isStockAdminFormVisible = false;
                            });
                          },
                          child: Icon(Icons.person_add),
                          heroTag: null,
                          backgroundColor: Colors.lightBlue,
                          tooltip: 'Create Engineer',
                        ),
                        SizedBox(width: 30),
                        FloatingActionButton(
                          onPressed: () {
                            setState(() {
                              _isStockAdminFormVisible = true;
                              _isProjectFormVisible = false;
                              _isEngineerFormVisible = false;
                            });
                          },
                          child: Icon(Icons.inventory),
                          heroTag: null,
                          backgroundColor: Colors.lightBlue,
                          tooltip: 'Create Stock Admin',
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            if (_isProjectFormVisible || _isEngineerFormVisible || _isStockAdminFormVisible)
              Positioned(
                top: 10,
                right: 10,
                child: IconButton(
                  icon: Icon(Icons.close),
                  onPressed: _showButtons,
                  tooltip: 'Close Form',
                ),
              ),
          ],
        ),
      ),
    );
  }
}
