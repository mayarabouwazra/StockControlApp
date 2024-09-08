import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Project {
  final int projectId;
  final String projectName;
  final String projectDescription;

  Project({required this.projectId, required this.projectName, required this.projectDescription});

  factory Project.fromJson(Map<String, dynamic> json) {
    return Project(
      projectId: json['projectId'],
      projectName: json['projectName'],
      projectDescription: json['projectDescription'],
    );
  }
}

class ProjectsPage extends StatefulWidget {
  @override
  _ProjectsPageState createState() => _ProjectsPageState();
}

class _ProjectsPageState extends State<ProjectsPage> {
  List<Project> projects = [];
  final String _apiUrl = 'http://localhost:8080/project';

  @override
  void initState() {
    super.initState();
    fetchProjects();
  }

  Future<void> fetchProjects() async {
    try {
      final response = await http.get(Uri.parse(_apiUrl));
      if (response.statusCode == 200) {
        setState(() {
          projects = (json.decode(response.body) as List)
              .map((project) => Project.fromJson(project))
              .toList();
        });
      } else {
        print('Failed to load projects');
      }
    } catch (e) {
      print('Error fetching projects: $e');
    }
  }

  void navigateToProjectDetails(Project project) {
    try {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => ProjectDetailPage(project: project),
        ),
      );
    } catch (e) {
      print('Error navigating to project details: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Projects')),
      body: projects.isEmpty
          ? Center(child: Text('No projects found'))
          : ListView.builder(
        itemCount: projects.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(projects[index].projectName),
            subtitle: Text(projects[index].projectDescription),
            onTap: () => navigateToProjectDetails(projects[index]),
          );
        },
      ),
    );
  }
}

class ProjectDetailPage extends StatelessWidget {
  final Project project;

  ProjectDetailPage({required this.project});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Project Details'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Project ID: ${project.projectId}'),
            Text('Project Name: ${project.projectName}'),
            Text('Project Description: ${project.projectDescription}'),
          ],
        ),
      ),
    );
  }
}