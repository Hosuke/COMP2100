/*
 *  Copyright (c) 2000-2002, Shayne R Flint
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  3. The name of the author may not be used to endorse or promote products
 *  derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package armidale.utilities.makeclass;

import armidale.api.util.Strings;
import java.io.PrintWriter;
import java.util.ListIterator;
import org.w3c.dom.Node;

class CallbackMethod extends Method {

  public CallbackMethod(Node node, ArmidaleClass theClass) {
    super(node, theClass);
  }


  public CallbackMethod(String name, ArmidaleClass theClass) {
    super(name, theClass);
    this.theClass = theClass;
  }


  private String spec(boolean synch) {
    ListIterator  parameterIterator  = parameterList.listIterator(0);
    Parameter     theParameter;
    String        result             = "  ";
    if (synch) {
      result = result + "synchronized ";
    }
    result = result + "public void " + name + "(" + Strings.capitalize(theClass.className.name) + " " + Strings.lowercase(theClass.className.name);
    while (parameterIterator.hasNext()) {
      theParameter = (Parameter) parameterIterator.next();
      result = result + ", " + theParameter.formalParameter();
    }
    return result + ")";
  }

  public String spec() {
    return spec(false);
  }
  
  public String synchronizedSpec() {
    return spec(true);
  }
  

  public String idName() {
    return Strings.lowercase(theClass.className.name) + Strings.capitalize(super.idName());
  }


  public String qualifiedName() {
    return Strings.lowercase(theClass.className.name) + Strings.capitalize(name);
  }

//   public void writeDocumentation(PrintWriter output) {
//     openDocumentation(output);
//     super.writeDocumentation(output);
//     output.println("   * @param " + Strings.lowercase(theClass.className.name)
//       + " the <tt>" + Strings.capitalize(theClass.className.name) + "</tt> object to which this event relates");
//     ListIterator  parameterIterator  = parameterList.listIterator(0);
//     Parameter     theParameter;
//     while (parameterIterator.hasNext()) {
//       theParameter = (Parameter) parameterIterator.next();
//       theParameter.writeDocumentation(output);
//     }
//     closeDocumentation(output);
//   }
//
}

