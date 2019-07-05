/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.finalController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public interface NomalUserOpControllerI extends LoginI,VerifiableI,ChangePassI {
}
