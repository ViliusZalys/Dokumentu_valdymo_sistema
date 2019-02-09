import React, { Component } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import CreateDocumentTypeComponent from "./CreateDocumentTypeComponent";

class CreateDocumentTypeConatainer extends Component {
  state = { title: "titleInState" };

  handleSubmit = () => {
    axios
      .post("http://localhost:8081/api/doctypes", {
        title: this.state.title
      })
      .then(function(response) {
        console.log(response);
      })
      .catch(function(error) {
        console.log(error);
      });
    console.log(">>>>>>>>>Submit happened");
  };
  render() {
    return <CreateDocumentTypeComponent onSubmit={this.handleSubmit} />;
  }
}

export default CreateDocumentTypeConatainer;