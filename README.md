# AndroidWithMVPBasic
======================

This sample showcases the following Architecture Components:
* [Model]
* [View]
* [Presenter]

Introduction
-------------
### Features

This sample contains two screens: a list of articles and detail view, that shows the detail of the article.

#### Model
  It is an interface responsible for managing data. 

#### View Layer
* A main activity that handles navigation between the fragments.
* A fragment to display the list of articles.(ArticleListFragmentView and ArticleListFragment)
* A fragment to display a article detail( ArticleDetailFragment : Basically it has a webview, that loads the url in it)


#### Presentation layer
* Presenter 
Presenter is the middlee-man between the View and Model. It encapsulates all the Presentation logic. The presenter is responsible for querying the model and updating the view, reacting to user interactions updating the model.

#### UnitTesting
JUinit Testcase has been written for Presenter. We can directly run in Android Studio by Right Clicking on the Class and select "Run <test class>
. And to see the coverage we have t the select "Run <test class> with Coverage"

License
--------

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
