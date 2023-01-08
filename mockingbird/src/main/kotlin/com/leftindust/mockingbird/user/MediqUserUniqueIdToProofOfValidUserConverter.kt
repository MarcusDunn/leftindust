package com.leftindust.mockingbird.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import org.springframework.beans.factory.annotation.Autowired

//@Component
//class MediqUserUniqueIdToProofOfValidUserConverter(private val firebaseAuth: FirebaseAuth) : FallibleConverter<MediqUserDto.MediqUserUniqueId, ProofOfValidUser> {
//    private val logger = KotlinLogging.logger { }
//
//    override fun convert(source: MediqUserDto.MediqUserUniqueId): ProofOfValidUser? {
//        try {
//            firebaseAuth.getUser(source.value)
//        } catch (e: FirebaseAuthException) {
//            logger.debug(e) { "[${source.value}] is not a valid firebase uid." }
//            logger.debug { FailedConversionMessage(source) }
//            return null
//        } catch (e: IllegalArgumentException) {
//            logger.debug { FailedConversionMessage(source) }
//            logger.debug(e) { "[${source.value}] is not a valid firebase uid." }
//            return null
//        }
//
//        return ProofOfValidUser
//    }
//}


fun MediqUserDto.MediqUserUniqueId.toProofOfValidUser(firebaseAuth: FirebaseAuth): Result4k<ProofOfValidUser, ConversionError<MediqUserDto.MediqUserUniqueId, ProofOfValidUser>> {


    try {
        firebaseAuth.getUser(value)
    } catch (e: FirebaseAuthException) {
        return ConversionFailure(
            Exception("[${value}] is not a valid firebase uid.")
        )
    } catch (e: IllegalArgumentException) {
        return ConversionFailure(
            Exception("[${value}] is not a valid firebase uid.")
        )
    }
    return Success(
        ProofOfValidUser
    )

}








