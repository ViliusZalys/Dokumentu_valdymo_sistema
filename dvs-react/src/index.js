import React from "react";
import ReactDOM from "react-dom";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";
import UserGroupsContainer from "./components/Forms/User/UserGroupsContainer";
import AdministrationContainer from "./components/Administration/AdministrationContainer";
import LandingPage from "./components/LandingPage";
import App from "./App";
import CreateUserGroupsContainer from "./components/UserGroups/CreateUserGroupsContainer";
import CreateUserGroupsForm from "./components/Forms/CreateUserGroupsForm";
import FileUploadContainer from "./components/FileUpload/FileUploadContainer";
import DocumentTypesContainer from "./components/DocumentTypes/DocumentTypesContainer";
import CreateDocumentTypeConatainer from "./components/Forms/DocumentType/CreateDocumentTypeContainer";
import CreateUserContainer from "./components/Forms/User/CreateUserContainer";
import UsersContainer from "./components/Users/UsersContainer";
import FileDownloadConatainer from "./components/FileDownload/FileDownloadContainer";
import LandingPageContainer from "./components/LandingPageContainer";
import DocumentsContainer from "./components/Documents/DocumentsContainer";

ReactDOM.render(
  <BrowserRouter>
    <App>
      <Switch>
        <Route exact path="/" component={LandingPageContainer} />
        <Route exact path="/usergroups" component={UserGroupsContainer} />
        <Route exact path="/usergroups/:id" />
        <Route exact path="/admin" component={AdministrationContainer} />
        <Route exact path="/upload" component={FileUploadContainer} />
        <Route exact path="/download" component={FileDownloadConatainer} />
        <Route
          exact
          path="/admin/newdoctype"
          component={CreateDocumentTypeConatainer}
        />
        <Route
          exact
          path="/admin/doctypes"
          component={DocumentTypesContainer}
        />{" "}
        <Route exact path="/admin/newuser" component={CreateUserContainer} />
        <Route exact path="/admin/users" component={UsersContainer} />
        <Route exact path="/admin/docs" component={DocumentsContainer} />
        <Route exact path="/docs" component={DocumentsContainer} />
        <Route exact path="/testinggroups" component={UserGroupsContainer} />
        <Route
          exact
          path="/admin/usergroups"
          component={CreateUserGroupsContainer}
        />
        <Route
          exact
          path="/admin/usergroups/new"
          component={CreateUserGroupsForm}
        />
      </Switch>
    </App>
  </BrowserRouter>,
  document.getElementById("root")
);
